package com.gitee.pristine.schedule.core;

import com.gitee.pristine.schedule.ex.ScheduleException;
import com.gitee.pristine.schedule.lang.JobParam;
import org.quartz.*;

import java.util.Date;

/**
 * 作业调度帮助类
 * @author Pristine Xu
 * @date 2021/12/28 21:16
 * @description:
 */
public class ScheduleExecutor {

    /**
     * 创建定时任务
     * @param job 定时任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static void createJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        // 获取 JobKey / JobDataMap / JobClass
        JobKey key = JobUtil.getJobKey(job);
        JobDataMap dataMap = job.getDataMap();
        dataMap.put("jobId", job.getJobId());
        dataMap.put("jobName", job.getJobName());
        Class<? extends Job> jobClass = null;
        try {
            jobClass = JobUtil.getClass(job.getInvokedClassPath());
        } catch (ClassNotFoundException e) {
            throw new SchedulerException(e.getMessage());
        }

        // 创建 JobDetail
        JobDetail jobDetail = JobUtil.getJobDetail(key, jobClass, dataMap);

        // 创建 Trigger
        Trigger trigger = null;
        if (!Boolean.TRUE.equals(job.getRunStatus())) {
            // 不立即触发
            Date date = new Date();
            trigger = Triggers.createCron(key, job.getJobCron(), dataMap, new Date(date.getTime() + 30 * 1000));
        } else {
            // 立即触发
            trigger = Triggers.createCron(key, job.getJobCron(), dataMap, new Date());
        }

        // 判断是否已经有了
        boolean existJob = scheduler.checkExists(key);
        if (existJob) {
            scheduler.deleteJob(key);
        }

        // 绑定 scheduler
        scheduler.scheduleJob(jobDetail, trigger);

        // 判断 刚创建的任务是否需要暂停
        if (!Boolean.TRUE.equals(job.getRunStatus())) {
            scheduler.pauseJob(key);
        } else {
            scheduler.resumeJob(key);
        }
    }


    /**
     * 重启任务
     * @param job 定时任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static void resumeJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        JobKey key = JobUtil.getJobKey(job);
        scheduler.resumeJob(key);
    }


    /**
     * 暂停任务
     * @param job 定时任务
     * @param scheduler 调度器
     */
    public static void pauseJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        JobKey key = JobUtil.getJobKey(job);
        scheduler.pauseJob(key);
    }


    /**
     * 删除任务
     * @param job 定时任务
     * @param scheduler 调度器
     * @throws SchedulerException
     */
    public static boolean removeJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        JobKey key = JobUtil.getJobKey(job);
        return scheduler.deleteJob(key);
    }


    /**
     * 触发任务（立即执行一次）
     * @param job 定时任务
     * @param scheduler 调度器
     * @return
     */
    public static void triggerJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        JobKey key = JobUtil.getJobKey(job);
        scheduler.triggerJob(key);
    }

    /**
     * 是否存在该任务
     * @param job
     * @param scheduler
     * @return
     * @throws SchedulerException
     */
    public static boolean existJob(JobParam job, Scheduler scheduler) throws SchedulerException {
        JobKey key = JobUtil.getJobKey(job);
        return scheduler.checkExists(key);
    }

}
