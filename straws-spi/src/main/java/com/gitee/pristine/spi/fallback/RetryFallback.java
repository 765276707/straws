package com.gitee.pristine.spi.fallback;

import com.gitee.pristine.spi.task.Chunk;

/**
 * 兜底业务
 * @author Pristine Xu
 * @date 2022/1/13 18:25
 * @description:
 */
public interface RetryFallback {

    void fallback(Chunk chunk);

}
