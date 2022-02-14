package com.gitee.pristine.core.container.biz;

import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.domain.entity.TaskLog;

/**
 * @author Pristine Xu
 * @date 2022/1/19 9:58
 * @description:
 */
public interface SyncTaskBiz {

    /**
     * 查询Task
     * @param id
     * @return
     */
    public Task getById(Long id);

    /**
     * 同步之后更新Task相关信息
     * @param task
     * @return
     */
    public boolean updateTaskAfterSync(Task task);

    /**
     * 获取数据源
     * @param dsId 数据源编号
     * @return
     */
    public DataSourceWrapper getDataSource(Long dsId);

    /**
     * 添加日志
     * @param log
     * @return
     */
    public boolean addTaskLog(TaskLog log);
}
