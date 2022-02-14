package com.gitee.pristine.spi.config;

/**
 * 任务配置
 * @author Pristine Xu
 * @date 2022/1/13 18:04
 * @description:
 */
public class Config {

    // 最大重试次数
    public static int maxRetryTimes = 3;

    // 等待重试时间
    public static int waitRetryInterval = 5000;

    // 重试失败的兜底策略
    public static RetryFallbackStrategy retryFallbackStrategy = RetryFallbackStrategy.THROW_EX;

    // 延迟重试时间，毫秒，当策略延迟重试时本参数有效
    public static long delayRetryTime = 10000;

}
