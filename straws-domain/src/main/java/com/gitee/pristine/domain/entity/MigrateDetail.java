package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_migrate_detail")
public class MigrateDetail {
    @Id
    private Long id;

    /**
     * 任务编号
     */
    @Column(name = "migrate_id")
    private Long migrateId;

    /**
     * 源头表的表名
     */
    @Column(name = "origin_table_name")
    private String originTableName;

    /**
     * 目标表的表名
     */
    @Column(name = "target_table_name")
    private String targetTableName;

    /**
     * 是否创建表
     */
    @Column(name = "is_create_table")
    private Boolean isCreateTable;

    private String transformers;

    /**
     * 状态，1：未执行， 2：已完成，3：未完成，4：错误中断
     */
    private Integer status;

    /**
     * 创建时间，同一批次的创建时间应相同
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 来源表的数据查询sql
     */
    @Column(name = "origin_table_select_sql")
    private String originTableSelectSql;

    @Column(name = "target_table_insert_sql")
    private String targetTableInsertSql;

    /**
     * 目标表的建表语句
     */
    @Column(name = "target_table_create_sql")
    private String targetTableCreateSql;

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
     * 获取任务编号
     *
     * @return migrate_id - 任务编号
     */
    public Long getMigrateId() {
        return migrateId;
    }

    /**
     * 设置任务编号
     *
     * @param migrateId 任务编号
     */
    public void setMigrateId(Long migrateId) {
        this.migrateId = migrateId;
    }

    /**
     * 获取源头表的表名
     *
     * @return origin_table_name - 源头表的表名
     */
    public String getOriginTableName() {
        return originTableName;
    }

    /**
     * 设置源头表的表名
     *
     * @param originTableName 源头表的表名
     */
    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName == null ? null : originTableName.trim();
    }

    /**
     * 获取目标表的表名
     *
     * @return target_table_name - 目标表的表名
     */
    public String getTargetTableName() {
        return targetTableName;
    }

    /**
     * 设置目标表的表名
     *
     * @param targetTableName 目标表的表名
     */
    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName == null ? null : targetTableName.trim();
    }

    /**
     * 获取是否创建表
     *
     * @return is_create_table - 是否创建表
     */
    public Boolean getIsCreateTable() {
        return isCreateTable;
    }

    /**
     * 设置是否创建表
     *
     * @param isCreateTable 是否创建表
     */
    public void setIsCreateTable(Boolean isCreateTable) {
        this.isCreateTable = isCreateTable;
    }

    /**
     * @return transformers
     */
    public String getTransformers() {
        return transformers;
    }

    /**
     * @param transformers
     */
    public void setTransformers(String transformers) {
        this.transformers = transformers == null ? null : transformers.trim();
    }

    /**
     * 获取状态，1：未执行， 2：已完成，3：未完成，4：错误中断
     *
     * @return status - 状态，1：未执行， 2：已完成，3：未完成，4：错误中断
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，1：未执行， 2：已完成，3：未完成，4：错误中断
     *
     * @param status 状态，1：未执行， 2：已完成，3：未完成，4：错误中断
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间，同一批次的创建时间应相同
     *
     * @return create_time - 创建时间，同一批次的创建时间应相同
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间，同一批次的创建时间应相同
     *
     * @param createTime 创建时间，同一批次的创建时间应相同
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
     * 获取来源表的数据查询sql
     *
     * @return origin_table_select_sql - 来源表的数据查询sql
     */
    public String getOriginTableSelectSql() {
        return originTableSelectSql;
    }

    /**
     * 设置来源表的数据查询sql
     *
     * @param originTableSelectSql 来源表的数据查询sql
     */
    public void setOriginTableSelectSql(String originTableSelectSql) {
        this.originTableSelectSql = originTableSelectSql == null ? null : originTableSelectSql.trim();
    }

    /**
     * @return target_table_insert_sql
     */
    public String getTargetTableInsertSql() {
        return targetTableInsertSql;
    }

    /**
     * @param targetTableInsertSql
     */
    public void setTargetTableInsertSql(String targetTableInsertSql) {
        this.targetTableInsertSql = targetTableInsertSql == null ? null : targetTableInsertSql.trim();
    }

    /**
     * 获取目标表的建表语句
     *
     * @return target_table_create_sql - 目标表的建表语句
     */
    public String getTargetTableCreateSql() {
        return targetTableCreateSql;
    }

    /**
     * 设置目标表的建表语句
     *
     * @param targetTableCreateSql 目标表的建表语句
     */
    public void setTargetTableCreateSql(String targetTableCreateSql) {
        this.targetTableCreateSql = targetTableCreateSql == null ? null : targetTableCreateSql.trim();
    }
}