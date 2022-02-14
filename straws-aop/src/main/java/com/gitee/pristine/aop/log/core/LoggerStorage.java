package com.gitee.pristine.aop.log.core;

/**
 * 日志保存接口
 * @author Pristine Xu
 * @date 2022/2/7 17:50
 * @description:
 */
public interface LoggerStorage {

    /**
     * 保存日志
     * @param log
     */
    void saveLog(Log log);

}
