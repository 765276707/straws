package com.gitee.pristine.spi.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 任务重试异常
 * @author Pristine Xu
 * @date 2022/1/14 19:08
 * @description:
 */
public class RetryFallbackException extends StrawsException {

    public RetryFallbackException(String message) {
        super(message);
    }

    public RetryFallbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
