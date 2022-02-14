package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.base.BaseObject;

/**
 * @author Pristine Xu
 * @date 2022/1/21 9:39
 * @description:
 */
public class TaskDTO extends BaseObject {

    private Long id;

    private String name;

    private String groupName;

    private Long originDsId;

    private Long targetDsId;

    private String originTableName;

    private String targetTableName;

    private Integer syncMode;

    /**
     * 进行任务切分的列名，必须为自增列
     */
    private String splitPk;

    /**
     * 进行任务切分的列类型，必须为int或long类型
     */
    private Integer splitPkType;

    private Long lastMinSplitPk;

    private Long lastMaxSplitPk;

    private String timeCron;

    private Integer workersPerGroup;

    private Integer bytesPerSecond;

    private Integer selectFetchSize;

    private Integer insertBatchSize;

    /**
     * 以逗号分割，最多存储5个
     */
    private String transforms;

    /**
     * 需要同步的列名，以逗号分割
     */
    private String columns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getOriginDsId() {
        return originDsId;
    }

    public void setOriginDsId(Long originDsId) {
        this.originDsId = originDsId;
    }

    public Long getTargetDsId() {
        return targetDsId;
    }

    public void setTargetDsId(Long targetDsId) {
        this.targetDsId = targetDsId;
    }

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public Integer getSyncMode() {
        return syncMode;
    }

    public void setSyncMode(Integer syncMode) {
        this.syncMode = syncMode;
    }

    public String getSplitPk() {
        return splitPk;
    }

    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk;
    }

    public Integer getSplitPkType() {
        return splitPkType;
    }

    public void setSplitPkType(Integer splitPkType) {
        this.splitPkType = splitPkType;
    }

    public Long getLastMinSplitPk() {
        return lastMinSplitPk;
    }

    public void setLastMinSplitPk(Long lastMinSplitPk) {
        this.lastMinSplitPk = lastMinSplitPk;
    }

    public Long getLastMaxSplitPk() {
        return lastMaxSplitPk;
    }

    public void setLastMaxSplitPk(Long lastMaxSplitPk) {
        this.lastMaxSplitPk = lastMaxSplitPk;
    }

    public String getTimeCron() {
        return timeCron;
    }

    public void setTimeCron(String timeCron) {
        this.timeCron = timeCron;
    }

    public Integer getWorkersPerGroup() {
        return workersPerGroup;
    }

    public void setWorkersPerGroup(Integer workersPerGroup) {
        this.workersPerGroup = workersPerGroup;
    }

    public Integer getBytesPerSecond() {
        return bytesPerSecond;
    }

    public void setBytesPerSecond(Integer bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
    }

    public Integer getSelectFetchSize() {
        return selectFetchSize;
    }

    public void setSelectFetchSize(Integer selectFetchSize) {
        this.selectFetchSize = selectFetchSize;
    }

    public Integer getInsertBatchSize() {
        return insertBatchSize;
    }

    public void setInsertBatchSize(Integer insertBatchSize) {
        this.insertBatchSize = insertBatchSize;
    }

    public String getTransforms() {
        return transforms;
    }

    public void setTransforms(String transforms) {
        this.transforms = transforms;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}
