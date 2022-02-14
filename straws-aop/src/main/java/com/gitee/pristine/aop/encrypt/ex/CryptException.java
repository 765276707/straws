package com.gitee.pristine.aop.encrypt.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 传输数据加解密异常
 * @author Pristine Xu
 * @date 2022/2/8 10:54
 * @description:
 */
public class CryptException extends StrawsException {

    public CryptException() {
    }

    public CryptException(String message) {
        super(message);
    }

    public CryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptException(Throwable cause) {
        super(cause);
    }

    public CryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
