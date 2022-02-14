package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_task_log")
public class TaskLog {
    @Id
    private Long id;

    /**
     * 定时同步任务编号
     */
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    /**
     * 任务开始执行时间
     */
    @Column(name = "task_start_time")
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    @Column(name = "task_finish_time")
    private Date taskFinishTime;

    /**
     * 执行结果，1：成功， 2：失败
     */
    @Column(name = "task_result")
    private Integer taskResult;

    /**
     * 传输的数据总数
     */
    @Column(name = "transfer_record_count")
    private Long transferRecordCount;

    /**
     * 传输的数据总字节数
     */
    @Column(name = "transfer_record_bytes")
    private String transferRecordBytes;

    /**
     * 传输平均花费时间（多个chunk）
     */
    @Column(name = "transfer_average_time")
    private String transferAverageTime;

    /**
     * 传输的平均速率
     */
    @Column(name = "transfer_average_speed")
    private String transferAverageSpeed;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 失败原因
     */
    @Column(name = "task_failed_reason")
    private String taskFailedReason;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取定时同步任务编号
     *
     * @return task_id - 定时同步任务编号
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置定时同步任务编号
     *
     * @param taskId 定时同步任务编号
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * @return task_name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * 获取任务开始执行时间
     *
     * @return task_start_time - 任务开始执行时间
     */
    public Date getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * 设置任务开始执行时间
     *
     * @param taskStartTime 任务开始执行时间
     */
    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    /**
     * 获取任务结束时间
     *
     * @return task_finish_time - 任务结束时间
     */
    public Date getTaskFinishTime() {
        return taskFinishTime;
    }

    /**
     * 设置任务结束时间
     *
     * @param taskFinishTime 任务结束时间
     */
    public void setTaskFinishTime(Date taskFinishTime) {
        this.taskFinishTime = taskFinishTime;
    }

    /**
     * 获取执行结果，1：成功， 2：失败
     *
     * @return task_result - 执行结果，1：成功， 2：失败
     */
    public Integer getTaskResult() {
        return taskResult;
    }

    /**
     * 设置执行结果，1：成功， 2：失败
     *
     * @param taskResult 执行结果，1：成功， 2：失败
     */
    public void setTaskResult(Integer taskResult) {
        this.taskResult = taskResult;
    }

    /**
     * 获取传输的数据总数
     *
     * @return transfer_record_count - 传输的数据总数
     */
    public Long getTransferRecordCount() {
        return transferRecordCount;
    }

    /**
     * 设置传输的数据总数
     *
     * @param transferRecordCount 传输的数据总数
     */
    public void setTransferRecordCount(Long transferRecordCount) {
        this.transferRecordCount = transferRecordCount;
    }

    /**
     * 获取传输的数据总字节数
     *
     * @return transfer_record_bytes - 传输的数据总字节数
     */
    public String getTransferRecordBytes() {
        return transferRecordBytes;
    }

    /**
     * 设置传输的数据总字节数
     *
     * @param transferRecordBytes 传输的数据总字节数
     */
    public void setTransferRecordBytes(String transferRecordBytes) {
        this.transferRecordBytes = transferRecordBytes == null ? null : transferRecordBytes.trim();
    }

    /**
     * 获取传输平均花费时间（多个chunk）
     *
     * @return transfer_average_time - 传输平均花费时间（多个chunk）
     */
    public String getTransferAverageTime() {
        return transferAverageTime;
    }

    /**
     * 设置传输平均花费时间（多个chunk）
     *
     * @param transferAverageTime 传输平均花费时间（多个chunk）
     */
    public void setTransferAverageTime(String transferAverageTime) {
        this.transferAverageTime = transferAverageTime == null ? null : transferAverageTime.trim();
    }

    /**
     * 获取传输的平均速率
     *
     * @return transfer_average_speed - 传输的平均速率
     */
    public String getTransferAverageSpeed() {
        return transferAverageSpeed;
    }

    /**
     * 设置传输的平均速率
     *
     * @param transferAverageSpeed 传输的平均速率
     */
    public void setTransferAverageSpeed(String transferAverageSpeed) {
        this.transferAverageSpeed = transferAverageSpeed == null ? null : transferAverageSpeed.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取失败原因
     *
     * @return task_failed_reason - 失败原因
     */
    public String getTaskFailedReason() {
        return taskFailedReason;
    }

    /**
     * 设置失败原因
     *
     * @param taskFailedReason 失败原因
     */
    public void setTaskFailedReason(String taskFailedReason) {
        this.taskFailedReason = taskFailedReason == null ? null : taskFailedReason.trim();
    }
}