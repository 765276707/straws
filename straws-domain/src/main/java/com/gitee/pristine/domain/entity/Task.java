package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_task")
public class Task {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String name;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "origin_ds_id")
    private Long originDsId;

    @Column(name = "origin_ds_name")
    private String originDsName;

    @Column(name = "target_ds_id")
    private Long targetDsId;

    @Column(name = "target_ds_name")
    private String targetDsName;

    @Column(name = "origin_table_name")
    private String originTableName;

    @Column(name = "target_table_name")
    private String targetTableName;

    @Column(name = "sync_mode")
    private Integer syncMode;

    /**
     * 进行任务切分的列名，必须为自增列
     */
    @Column(name = "split_pk")
    private String splitPk;

    /**
     * 进行任务切分的列类型，必须为int或long类型
     */
    @Column(name = "split_pk_type")
    private Integer splitPkType;

    @Column(name = "last_min_split_pk")
    private Long lastMinSplitPk;

    @Column(name = "last_max_split_pk")
    private Long lastMaxSplitPk;

    @Column(name = "time_cron")
    private String timeCron;

    @Column(name = "workers_per_group")
    private Integer workersPerGroup;

    @Column(name = "bytes_per_second")
    private Integer bytesPerSecond;

    @Column(name = "select_fetch_size")
    private Integer selectFetchSize;

    @Column(name = "insert_batch_size")
    private Integer insertBatchSize;

    /**
     * 以逗号分割，最多存储5个
     */
    private String transforms;

    @Column(name = "run_status")
    private Boolean runStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 需要同步的列名，以逗号分割
     */
    private String columns;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return group_name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * @return origin_ds_id
     */
    public Long getOriginDsId() {
        return originDsId;
    }

    /**
     * @param originDsId
     */
    public void setOriginDsId(Long originDsId) {
        this.originDsId = originDsId;
    }

    /**
     * @return origin_ds_name
     */
    public String getOriginDsName() {
        return originDsName;
    }

    /**
     * @param originDsName
     */
    public void setOriginDsName(String originDsName) {
        this.originDsName = originDsName == null ? null : originDsName.trim();
    }

    /**
     * @return target_ds_id
     */
    public Long getTargetDsId() {
        return targetDsId;
    }

    /**
     * @param targetDsId
     */
    public void setTargetDsId(Long targetDsId) {
        this.targetDsId = targetDsId;
    }

    /**
     * @return target_ds_name
     */
    public String getTargetDsName() {
        return targetDsName;
    }

    /**
     * @param targetDsName
     */
    public void setTargetDsName(String targetDsName) {
        this.targetDsName = targetDsName == null ? null : targetDsName.trim();
    }

    /**
     * @return origin_table_name
     */
    public String getOriginTableName() {
        return originTableName;
    }

    /**
     * @param originTableName
     */
    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName == null ? null : originTableName.trim();
    }

    /**
     * @return target_table_name
     */
    public String getTargetTableName() {
        return targetTableName;
    }

    /**
     * @param targetTableName
     */
    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName == null ? null : targetTableName.trim();
    }

    /**
     * @return sync_mode
     */
    public Integer getSyncMode() {
        return syncMode;
    }

    /**
     * @param syncMode
     */
    public void setSyncMode(Integer syncMode) {
        this.syncMode = syncMode;
    }

    /**
     * 获取进行任务切分的列名，必须为自增列
     *
     * @return split_pk - 进行任务切分的列名，必须为自增列
     */
    public String getSplitPk() {
        return splitPk;
    }

    /**
     * 设置进行任务切分的列名，必须为自增列
     *
     * @param splitPk 进行任务切分的列名，必须为自增列
     */
    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk == null ? null : splitPk.trim();
    }

    /**
     * 获取进行任务切分的列类型，必须为int或long类型
     *
     * @return split_pk_type - 进行任务切分的列类型，必须为int或long类型
     */
    public Integer getSplitPkType() {
        return splitPkType;
    }

    /**
     * 设置进行任务切分的列类型，必须为int或long类型
     *
     * @param splitPkType 进行任务切分的列类型，必须为int或long类型
     */
    public void setSplitPkType(Integer splitPkType) {
        this.splitPkType = splitPkType;
    }

    /**
     * @return last_min_split_pk
     */
    public Long getLastMinSplitPk() {
        return lastMinSplitPk;
    }

    /**
     * @param lastMinSplitPk
     */
    public void setLastMinSplitPk(Long lastMinSplitPk) {
        this.lastMinSplitPk = lastMinSplitPk;
    }

    /**
     * @return last_max_split_pk
     */
    public Long getLastMaxSplitPk() {
        return lastMaxSplitPk;
    }

    /**
     * @param lastMaxSplitPk
     */
    public void setLastMaxSplitPk(Long lastMaxSplitPk) {
        this.lastMaxSplitPk = lastMaxSplitPk;
    }

    /**
     * @return time_cron
     */
    public String getTimeCron() {
        return timeCron;
    }

    /**
     * @param timeCron
     */
    public void setTimeCron(String timeCron) {
        this.timeCron = timeCron == null ? null : timeCron.trim();
    }

    /**
     * @return workers_per_group
     */
    public Integer getWorkersPerGroup() {
        return workersPerGroup;
    }

    /**
     * @param workersPerGroup
     */
    public void setWorkersPerGroup(Integer workersPerGroup) {
        this.workersPerGroup = workersPerGroup;
    }

    /**
     * @return bytes_per_second
     */
    public Integer getBytesPerSecond() {
        return bytesPerSecond;
    }

    /**
     * @param bytesPerSecond
     */
    public void setBytesPerSecond(Integer bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
    }

    /**
     * @return select_fetch_size
     */
    public Integer getSelectFetchSize() {
        return selectFetchSize;
    }

    /**
     * @param selectFetchSize
     */
    public void setSelectFetchSize(Integer selectFetchSize) {
        this.selectFetchSize = selectFetchSize;
    }

    /**
     * @return insert_batch_size
     */
    public Integer getInsertBatchSize() {
        return insertBatchSize;
    }

    /**
     * @param insertBatchSize
     */
    public void setInsertBatchSize(Integer insertBatchSize) {
        this.insertBatchSize = insertBatchSize;
    }

    /**
     * 获取以逗号分割，最多存储5个
     *
     * @return transforms - 以逗号分割，最多存储5个
     */
    public String getTransforms() {
        return transforms;
    }

    /**
     * 设置以逗号分割，最多存储5个
     *
     * @param transforms 以逗号分割，最多存储5个
     */
    public void setTransforms(String transforms) {
        this.transforms = transforms == null ? null : transforms.trim();
    }

    /**
     * @return run_status
     */
    public Boolean getRunStatus() {
        return runStatus;
    }

    /**
     * @param runStatus
     */
    public void setRunStatus(Boolean runStatus) {
        this.runStatus = runStatus;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取需要同步的列名，以逗号分割
     *
     * @return columns - 需要同步的列名，以逗号分割
     */
    public String getColumns() {
        return columns;
    }

    /**
     * 设置需要同步的列名，以逗号分割
     *
     * @param columns 需要同步的列名，以逗号分割
     */
    public void setColumns(String columns) {
        this.columns = columns == null ? null : columns.trim();
    }
}