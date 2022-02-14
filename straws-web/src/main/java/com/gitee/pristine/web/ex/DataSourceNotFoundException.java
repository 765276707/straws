package com.gitee.pristine.web.ex;

/**
 * 数据源未找到异常
 * @author Pristine Xu
 * @date 2022/1/22 6:25
 * @description:
 */
public class DataSourceNotFoundException extends ServiceException {

    public DataSourceNotFoundException() {
        super();
    }

    public DataSourceNotFoundException(String message) {
        super(message);
    }

    public DataSourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
