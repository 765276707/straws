package com.gitee.pristine.spi.fallback;

import com.gitee.pristine.spi.task.Chunk;

/**
 * @author Pristine Xu
 * @date 2022/1/13 19:06
 * @description:
 */
public class SimpRetryFallback implements RetryFallback {

    @Override
    public void fallback(Chunk chunk) {
        System.out.println("正在执行兜底策略");
    }

}
