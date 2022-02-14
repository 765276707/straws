package com.gitee.pristine.core.executor;

import com.gitee.pristine.spi.ex.RetryFallbackException;
import com.gitee.pristine.spi.task.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.*;

/**
 * 延迟重试执行器
 * @author Pristine Xu
 * @date 2022/1/14 7:03
 * @description:
 */
public class ChunkRetryExecutor {

    private static Logger log = LoggerFactory.getLogger(ChunkRetryExecutor.class);
    // 管理器实例（线程可见）
    private static volatile ChunkRetryExecutor instance;
    // 存放执行失败的延迟队列, TODO 延迟队列为无界队列，需要小心 OOM
    private static DelayQueue<Chunk> retryTaskQueue = new DelayQueue<>();
    // 重试线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public ChunkRetryExecutor() {
        // 开启线程消费队列
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Chunk task = null;
                    try {
                        task = retryTaskQueue.take();
                        task.service();
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("Retry task failed. Cause by -> {}", e.getMessage());
                        }
                        throw new RetryFallbackException("Retry task failed.", e);
                    }
                }
            }
        });
    }

    // 获取 Manager 单例
    public static ChunkRetryExecutor getInstance() {
        if (instance == null) {
            synchronized (ChunkRetryExecutor.class) {
                if (instance == null) {
                    instance = new ChunkRetryExecutor();
                }
            }
        }
        return instance;
    }

    public void addRetryTask(Chunk task) {
        if (retryTaskQueue.size() > 100000) {
            throw new RetryFallbackException("Current retry tasks in queue too many.");
        }
        retryTaskQueue.offer(task);
    }

}
