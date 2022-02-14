package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_datasource")
public class Datasource {
    @Id
    private Long id;

    /**
     * 数据源名称，必须唯一
     */
    private String name;

    /**
     * 数据源状态，1：未启用 2：启用 
     */
    private Integer status;

    /**
     * 数据源类型，对用字典code：database_type
     */
    @Column(name = "type_id")
    private String typeId;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "driver_class_name")
    private String driverClassName;

    @Column(name = "driver_version")
    private String driverVersion;

    @Column(name = "jdbc_url")
    private String jdbcUrl;

    private String username;

    private String password;

    private String remark;

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
     * 获取数据源名称，必须唯一
     *
     * @return name - 数据源名称，必须唯一
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据源名称，必须唯一
     *
     * @param name 数据源名称，必须唯一
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取数据源状态，1：未启用 2：启用 
     *
     * @return status - 数据源状态，1：未启用 2：启用 
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置数据源状态，1：未启用 2：启用 
     *
     * @param status 数据源状态，1：未启用 2：启用 
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取数据源类型，对用字典code：database_type
     *
     * @return type_id - 数据源类型，对用字典code：database_type
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * 设置数据源类型，对用字典code：database_type
     *
     * @param typeId 数据源类型，对用字典code：database_type
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    /**
     * @return type_name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * @return driver_class_name
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @param driverClassName
     */
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName == null ? null : driverClassName.trim();
    }

    /**
     * @return driver_version
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * @param driverVersion
     */
    public void setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion == null ? null : driverVersion.trim();
    }

    /**
     * @return jdbc_url
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * @param jdbcUrl
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl == null ? null : jdbcUrl.trim();
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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