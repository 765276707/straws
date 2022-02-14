package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.core.constant.JobMapKey;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.TaskDTO;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.schedule.core.ScheduleExecutor;
import com.gitee.pristine.schedule.lang.JobParam;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.TaskMapper;
import com.gitee.pristine.web.service.SysDatasourceService;
import com.gitee.pristine.web.service.SysTaskService;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:32
 * @description:
 */
@Service
public class SysTaskServiceImpl implements SysTaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private SysDatasourceService sysDatasourceService;
    @Resource
    private Scheduler scheduler;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Task getById(Long id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public Task getByName(String name) {
        Task condition = new Task();
        condition.setName(name);
        return taskMapper.selectOne(condition);
    }

    @Override
    public List<Task> getList(TextCondition condition) {
        return taskMapper.selectList(condition);
    }

    @Override
    public List<Task> getAll() {
        return taskMapper.selectAll();
    }

    @Override
    @Transactional
    public int insertEntity(TaskDTO entity) {
        // 判断 name 是否已存在
        Task var = this.getByName(entity.getName());
        if (var != null) {
            throw new ServiceException("任务名称已被使用");
        }
        // 判断数据源是否存在
        Datasource originDataSource = sysDatasourceService.getById(entity.getOriginDsId());
        if (originDataSource == null) {
            throw new ServiceException("源头数据源不存在");
        }
        Datasource targetDataSource = sysDatasourceService.getById(entity.getTargetDsId());
        if (targetDataSource == null) {
            throw new ServiceException("目标数据源不存在");
        }
        // TODO 判断表是否存在
        // TODO 判断字段是否存在
        // 赋值
        Task task = new Task();
        BeanUtils.copyProperties(entity, task);
        task.setRunStatus(false);
        task.setCreateTime(new Date());
        // 插入数据库
        int rows = taskMapper.insertSelective(task);
        // 启动容器
        this.createJob(task);
        return rows;
    }

    @Override
    @Transactional
    public int updateEntity(TaskDTO entity) {
        // 判断 记录是否存在
        Task var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("更新的数据不存在");
        }
        // 判断 name 是否已被使用
        Task var2 = this.getByName(entity.getName());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("任务名称已被使用");
        }
        // 判断数据源是否存在
        Datasource originDataSource = sysDatasourceService.getById(entity.getOriginDsId());
        if (originDataSource == null) {
            throw new ServiceException("源头数据源不存在");
        }
        Datasource targetDataSource = sysDatasourceService.getById(entity.getTargetDsId());
        if (targetDataSource == null) {
            throw new ServiceException("目标数据源不存在");
        }
        // TODO 判断表是否存在
        // TODO 判断字段是否存在
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        // 更新数据库
        int rows = taskMapper.updateByPrimaryKey(var1);
        // 更新定时任务
        this.createJob(var1);
        return rows;
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return taskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void resumeOrPauseTask(Long id) {
        Task task = this.getById(id);
        if (task == null) {
            throw new ServiceException("定时任务不存在");
        }
        Boolean runStatus = !task.getRunStatus();
        task.setRunStatus(runStatus);
        task.setUpdateTime(new Date());
        taskMapper.updateByPrimaryKey(task);
        // 判断是要暂停还是恢复
        if (runStatus) {
            this.resumeJob(task);
        } else {
            this.pauseJob(task);
        }
    }

    @Override
    public void triggerTask(Long id) {
        Task task = this.getById(id);
        if (task == null) {
            throw new ServiceException("定时任务不存在");
        }
        this.triggerJob(task);
    }

    /**
     * 初始化定时任务
     * @param task
     */
    public void createJob(Task task) {
        // JobDataMap
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobMapKey.TASK_ID, task.getId());
        jobDataMap.put(JobMapKey.SPRING_IOC, applicationContext);
        // JobParam
        JobParam jobParam = new JobParam();
        jobParam.setJobId(task.getId());
        jobParam.setJobName(task.getName());
        jobParam.setJobGroup(task.getGroupName());
        jobParam.setJobCron(task.getTimeCron());
        jobParam.setDataMap(jobDataMap);
        jobParam.setInvokedClassPath("com.gitee.pristine.core.container.DefaultSyncContainer");
        jobParam.setRunStatus(task.getRunStatus());
        try {
            ScheduleExecutor.createJob(jobParam, scheduler);
        } catch (SchedulerException e) {
            throw new ServiceException("定时任务初始化失败");
        }
    }

    private void resumeJob(Task task) {
        JobParam jobParam = new JobParam();
        jobParam.setJobId(task.getId());
        jobParam.setJobName(task.getName());
        jobParam.setJobGroup(task.getGroupName());
        try {
            // 判断是否存在该任务
            ScheduleExecutor.resumeJob(jobParam, scheduler);
        } catch (SchedulerException e) {
            throw new ServiceException("恢复定时任务失败");
        }
    }

    private void pauseJob(Task task) {
        JobParam jobParam = new JobParam();
        jobParam.setJobId(task.getId());
        jobParam.setJobName(task.getName());
        jobParam.setJobGroup(task.getGroupName());
        jobParam.setRunStatus(false);
        try {
            // 判断是否存在该任务
            ScheduleExecutor.pauseJob(jobParam, scheduler);
        } catch (SchedulerException e) {
            throw new ServiceException("暂停定时任务失败");
        }
    }

    private void triggerJob(Task task) {
        JobParam jobParam = new JobParam();
        jobParam.setJobId(task.getId());
        jobParam.setJobName(task.getName());
        jobParam.setJobGroup(task.getGroupName());
        try {
            // 判断是否存在该任务
            ScheduleExecutor.triggerJob(jobParam, scheduler);
        } catch (SchedulerException e) {
            throw new ServiceException("触发定时任务失败");
        }
    }

}
