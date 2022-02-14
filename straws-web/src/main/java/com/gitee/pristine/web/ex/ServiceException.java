package com.gitee.pristine.web.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 业务执行异常
 * @author Pristine Xu
 * @date 2022/1/19 5:12
 * @description:
 */
public class ServiceException extends StrawsException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
