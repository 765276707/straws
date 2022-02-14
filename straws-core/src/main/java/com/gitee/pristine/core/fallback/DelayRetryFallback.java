package com.gitee.pristine.core.fallback;

import com.gitee.pristine.core.executor.ChunkRetryExecutor;
import com.gitee.pristine.spi.fallback.RetryFallback;
import com.gitee.pristine.spi.task.Chunk;

/**
 * 延迟重试兜底策略实现
 * @author Pristine Xu
 * @date 2022/1/14 7:06
 * @description: 在到达指定延迟时间之后，进行任务重试，如果继续失败则抛出异常并记录
 */
public class DelayRetryFallback implements RetryFallback {

    @Override
    public void fallback(Chunk chunk) {
        // TODO 目前的设计思路比较简单，后续将进行改进
        ChunkRetryExecutor.getInstance().addRetryTask(chunk);
    }

}
