package com.gitee.pristine.common.ex;

/**
 * 框架编译时异常
 * @author Pristine Xu
 * @date 2022/1/13 4:11
 * @description:
 */
public class StrawsError extends Error {

    public StrawsError() {
    }

    public StrawsError(String message) {
        super(message);
    }

    public StrawsError(String message, Throwable cause) {
        super(message, cause);
    }

    public StrawsError(Throwable cause) {
        super(cause);
    }

    public StrawsError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
