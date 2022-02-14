package com.gitee.pristine.schedule.core;

import com.gitee.pristine.schedule.lang.JobParam;
import org.quartz.*;

/**
 * @author Pristine Xu
 * @date 2021/12/28 21:14
 * @description:
 */
public class JobUtil {

    /**
     * 获取任务的标识
     * @param jobParam 定时任务
     * @return
     */
    public static JobKey getJobKey(JobParam jobParam) {
        return new JobKey(jobParam.getJobName(), jobParam.getJobGroup());
    }


    /**
     *  获取任务类
     * @param classPath 类路径
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Job> getClass(String classPath) throws ClassNotFoundException {
        return (Class<? extends Job>) Class.forName(classPath);
    }


    /**
     * 获取 JobDetail
     * @param key 组合键
     * @param jobClass 定时任务类
     * @param dataMap 参数
     * @return
     */
    public static JobDetail getJobDetail(JobKey key, Class<? extends Job> jobClass, JobDataMap dataMap) {
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(key)
                .usingJobData(dataMap)
                .storeDurably()
                .build();
    }

}
