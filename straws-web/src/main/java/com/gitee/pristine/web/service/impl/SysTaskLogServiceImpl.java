package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.condition.TaskLogCondition;
import com.gitee.pristine.domain.entity.TaskLog;
import com.gitee.pristine.web.mapper.TaskLogMapper;
import com.gitee.pristine.web.service.SysTaskLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:32
 * @description:
 */
@Service
public class SysTaskLogServiceImpl implements SysTaskLogService {

    private final Logger log = LoggerFactory.getLogger(SysTaskLogServiceImpl.class);
    @Resource
    private TaskLogMapper taskLogMapper;

    @Override
    public List<TaskLog> getList(TaskLogCondition condition) {
        Example example = new Example(TaskLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", condition.getTaskId());
        example.orderBy("taskStartTime").desc();
        return taskLogMapper.selectByExample(example);
    }

    @Override
    public List<TaskLog> getLatestTaskLogs() {
        return taskLogMapper.selectLatestTaskLogs();
    }

    @Override
    @Transactional
    public int insertEntity(TaskLog taskLog) {
        return taskLogMapper.insertSelective(taskLog);
    }
}
