package com.gitee.pristine.schedule.core;

import org.quartz.*;

import java.util.Date;

/**
 * Quartz触发器工具类
 * @author Pristine Xu
 * @date 2021/12/28 21:33
 * @description:
 */
public class Triggers {

    /**
     * 创建只自动执行一次的 Simple Trigger
     * @param key 组合键
     * @param intervalInSeconds 启动后多久执行，秒
     * @param dataMap 参数
     * @return
     */
    public static Trigger createSimple(JobKey key, int intervalInSeconds, JobDataMap dataMap, Date startTime) {
        return TriggerBuilder.newTrigger()
                .withIdentity(key.getName(), key.getGroup())
                .usingJobData(dataMap)
                // 启动时间
                .startAt(new Date())
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .withRepeatCount(0))
                .build();
    }

    /**
     * 创建 Cron Trigger
     * @param key 组合键
     * @param cron 定时表达式
     * @param dataMap 参数
     * @return
     */
    public static Trigger createCron(JobKey key, String cron, JobDataMap dataMap, Date startTime) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(key.getName(), key.getGroup())
                .usingJobData(dataMap)
                // CRON 表达式
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                // 启动时间
                .startAt(startTime)
                .build();
    }
}
