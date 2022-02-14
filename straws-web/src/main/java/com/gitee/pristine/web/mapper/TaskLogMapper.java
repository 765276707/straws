package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.entity.TaskLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskLogMapper extends Mapper<TaskLog> {

    /**
     * 获取最新的10条定时任务日志
     * @return
     */
    List<TaskLog> selectLatestTaskLogs();
}