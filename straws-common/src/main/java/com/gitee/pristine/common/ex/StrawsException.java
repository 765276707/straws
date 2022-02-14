package com.gitee.pristine.common.ex;

/**
 * 框架运行时异常
 * @author Pristine Xu
 * @date 2022/1/13 4:11
 * @description:
 */
public class StrawsException extends RuntimeException {

    public StrawsException() {
    }

    public StrawsException(String message) {
        super(message);
    }

    public StrawsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StrawsException(Throwable cause) {
        super(cause);
    }

    public StrawsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
