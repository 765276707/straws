package com.gitee.pristine.schedule.ex;

/**
 * 容器初始化异常
 * @author Pristine Xu
 * @date 2022/1/14 5:54
 * @description:
 */
public class ContainerInitException extends ScheduleException {

    public ContainerInitException() {
    }

    public ContainerInitException(String message) {
        super(message);
    }

    public ContainerInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContainerInitException(Throwable cause) {
        super(cause);
    }

    public ContainerInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
