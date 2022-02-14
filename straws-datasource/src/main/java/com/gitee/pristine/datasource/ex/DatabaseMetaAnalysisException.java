package com.gitee.pristine.datasource.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 数据库元信息解析异常
 * @author Pristine Xu
 * @date 2022/1/13 19:34
 * @description:
 */
public class DatabaseMetaAnalysisException extends StrawsException {

    public DatabaseMetaAnalysisException() {
    }

    public DatabaseMetaAnalysisException(String message) {
        super(message);
    }

    public DatabaseMetaAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseMetaAnalysisException(Throwable cause) {
        super(cause);
    }

    public DatabaseMetaAnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
