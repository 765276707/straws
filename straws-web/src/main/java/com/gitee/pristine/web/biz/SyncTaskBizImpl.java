package com.gitee.pristine.web.biz;

import com.gitee.pristine.core.container.biz.SyncTaskBiz;
import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.domain.entity.TaskLog;
import com.gitee.pristine.web.manager.DataSourceManager;
import com.gitee.pristine.web.mapper.TaskLogMapper;
import com.gitee.pristine.web.mapper.TaskMapper;
import com.gitee.pristine.web.service.SysTaskLogService;
import com.gitee.pristine.web.service.SysTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时同步作业中所需要业务
 * @author Pristine Xu
 * @date 2022/1/19 9:47
 * @description:
 */
@Component
public class SyncTaskBizImpl implements SyncTaskBiz {

    @Resource
    private DataSourceManager dataSourceManager;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskLogMapper taskLogMapper;

    @Override
    public Task getById(Long id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateTaskAfterSync(Task task) {
        int rows = taskMapper.updateByPrimaryKey(task);
        return rows > 0;
    }

    @Override
    public DataSourceWrapper getDataSource(Long dsId) {
        return dataSourceManager.getDataSource(dsId);
    }

    @Override
    public boolean addTaskLog(TaskLog log) {
        int rows = taskLogMapper.insertSelective(log);
        return rows > 0;
    }

}
