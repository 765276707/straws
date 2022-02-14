package com.gitee.pristine.spi.task;

import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.spi.config.Config;
import com.gitee.pristine.spi.ex.ChunkInitException;
import com.gitee.pristine.spi.fallback.RetryFallback;
import com.gitee.pristine.spi.result.SyncResult;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 可重试的 Chunk
 * @author Pristine Xu
 * @date 2022/1/13 18:02
 * @description:
 */
public abstract class RetryableChunk implements Chunk {

    // 开始进入重试队列的时间
    private long startRetryTime = 0;

    @Override
    public SyncResult call() throws Exception {
        // 是否需要重试
        boolean isRetry = true;
        // 当前重试次数
        int curRetryTimes = 1;
        // 最大重试次数
        int maxRetryTimes = Config.maxRetryTimes;
        // 等待重试时间
        int waitRetryInterval = Config.waitRetryInterval;
        // 执行结果
        SyncResult result = null;

        do {
            try {
                ++ curRetryTimes;
                result = this.service();
                // 结束循环
                isRetry = false;
            } catch (Throwable e) {
                // 判断是否需要睡眠等待
                if (curRetryTimes <= maxRetryTimes) {
                    Thread.sleep(waitRetryInterval);
                } else {
                    // 最后一次，仍然捕获到异常，则进行彻底的兜底处理
                    switch (Config.retryFallbackStrategy) {
                        case RETRY_LATER:

                            RetryFallback retryFallback = this.registerRetryFallback();
                            if (retryFallback == null) {
                                throw new ChunkInitException("RetryFallback not registered in Chunk.");
                            }
                            this.startRetryTime = DateUtil.getTimeStamp();
                            retryFallback.fallback(this);

                            break;
                        case THROW_EX:
                            throw e;
                    }
                }
            }
        }
        while (isRetry && curRetryTimes<=maxRetryTimes);

        return result;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // 默认延迟 20 秒
        return unit.convert(startRetryTime+Config.delayRetryTime - DateUtil.getTimeStamp(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    /**
     * 注册兜底处理类
     */
    protected abstract RetryFallback registerRetryFallback();

}
