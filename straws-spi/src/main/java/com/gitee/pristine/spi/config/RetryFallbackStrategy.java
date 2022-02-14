package com.gitee.pristine.spi.config;

/**
 * Chunk内重试失败之后的兜底策略
 * @author Pristine Xu
 * @date 2022/1/13 18:13
 * @description:
 */
public enum RetryFallbackStrategy {

    // 直接抛出异常
    THROW_EX,

    // 将失败的Chunk放入重试线程池中，等待进行重试
    RETRY_LATER;

}
