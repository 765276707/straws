package com.gitee.pristine.core.chunk;

import com.gitee.pristine.core.fallback.DelayRetryFallback;
import com.gitee.pristine.spi.fallback.RetryFallback;
import com.gitee.pristine.spi.task.RetryableChunk;

/**
 * @author Pristine Xu
 * @date 2022/1/16 7:00
 * @description:
 */
public abstract class DelayRetryableChunk extends RetryableChunk {

    @Override
    protected RetryFallback registerRetryFallback() {
        return new DelayRetryFallback();
    }

}
