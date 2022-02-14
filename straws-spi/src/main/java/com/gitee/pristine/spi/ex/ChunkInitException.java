package com.gitee.pristine.spi.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * Chunk初始化异常
 * @author Pristine Xu
 * @date 2022/1/13 19:01
 * @description:
 */
public class ChunkInitException extends StrawsException {

    public ChunkInitException() {
    }

    public ChunkInitException(String message) {
        super(message);
    }

    public ChunkInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChunkInitException(Throwable cause) {
        super(cause);
    }

    public ChunkInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
