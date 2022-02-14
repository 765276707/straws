package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.condition.TaskLogCondition;
import com.gitee.pristine.domain.entity.TaskLog;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:30
 * @description:
 */
public interface SysTaskLogService {

    List<TaskLog> getList(TaskLogCondition condition);

    List<TaskLog> getLatestTaskLogs();

    int insertEntity(TaskLog taskLog);

}
