package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_migrate")
public class Migrate {
    @Id
    private Long id;

    private String name;

    @Column(name = "origin_ds_id")
    private Long originDsId;

    @Column(name = "origin_ds_name")
    private String originDsName;

    @Column(name = "target_ds_id")
    private Long targetDsId;

    @Column(name = "target_ds_name")
    private String targetDsName;

    /**
     * 转换器脚本编号集合
     */
    private String transformers;

    @Column(name = "exec_time")
    private Date execTime;

    /**
     * 执行结果，1：未开始，2：已完成，3：未完成
     */
    @Column(name = "exec_result")
    private Integer execResult;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取转换器脚本编号集合
     *
     * @return transformers - 转换器脚本编号集合
     */
    public String getTransformers() {
        return transformers;
    }

    /**
     * 设置转换器脚本编号集合
     *
     * @param transformers 转换器脚本编号集合
     */
    public void setTransformers(String transformers) {
        this.transformers = transformers == null ? null : transformers.trim();
    }

    /**
     * @return exec_time
     */
    public Date getExecTime() {
        return execTime;
    }

    /**
     * @param execTime
     */
    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    /**
     * 获取执行结果，1：未开始，2：已完成，3：未完成
     *
     * @return exec_result - 执行结果，1：未开始，2：已完成，3：未完成
     */
    public Integer getExecResult() {
        return execResult;
    }

    /**
     * 设置执行结果，1：未开始，2：已完成，3：未完成
     *
     * @param execResult 执行结果，1：未开始，2：已完成，3：未完成
     */
    public void setExecResult(Integer execResult) {
        this.execResult = execResult;
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
}