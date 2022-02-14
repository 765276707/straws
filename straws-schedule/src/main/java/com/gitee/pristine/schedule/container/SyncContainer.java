package com.gitee.pristine.schedule.container;

import com.gitee.pristine.spi.result.SyncResult;
import com.gitee.pristine.spi.task.Chunk;
import org.quartz.Job;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * 同步容器
 * @author Pristine Xu
 * @date 2022/1/14 5:44
 * @description:
 */
public interface SyncContainer extends Job {

    // 初始化
    void init(long taskId) throws JobExecutionException;

    // 任务切分
    List<Chunk> split();

    // 执行任务
    SyncResult sync(List<Chunk> chunks) throws Exception;

    // 后置处理
    void post(SyncResult result);

    // 失败操作
    void fallback(Exception e);

}
