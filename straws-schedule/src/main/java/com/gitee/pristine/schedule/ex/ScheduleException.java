package com.gitee.pristine.schedule.ex;

import com.gitee.pristine.common.ex.StrawsException;

/**
 * 调度器异常
 * @author Pristine Xu
 * @date 2022/1/14 5:54
 * @description:
 */
public class ScheduleException extends StrawsException {

    public ScheduleException() {
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }

    public ScheduleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
