package com.gitee.pristine.schedule.lang;

import org.quartz.JobDataMap;

import java.util.Date;

/**
 * 作业参数
 * @author Pristine Xu
 * @date 2021/12/28 21:12
 * @description:
 */
public class JobParam {

    private Long jobId;

    private String jobName;

    private String jobDesc;

    private String jobGroup;

    private String invokedClassPath;

    private String jobCron;

    private JobDataMap dataMap;

    private Boolean runStatus;

    private Date createTime;

    private Date updateTime;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getInvokedClassPath() {
        return invokedClassPath;
    }

    public void setInvokedClassPath(String invokedClassPath) {
        this.invokedClassPath = invokedClassPath;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public JobDataMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(JobDataMap dataMap) {
        this.dataMap = dataMap;
    }

    public Boolean getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Boolean runStatus) {
        this.runStatus = runStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
