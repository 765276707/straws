package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_script")
public class Script {
    @Id
    private Long id;

    private String name;

    @Column(name = "hash_key")
    private String hashKey;

    private String description;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    /**
     * 是否通过安全检测
     */
    @Column(name = "is_checked")
    private Boolean isChecked;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String content;

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
     * @return hash_key
     */
    public String getHashKey() {
        return hashKey;
    }

    /**
     * @param hashKey
     */
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey == null ? null : hashKey.trim();
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取是否启用
     *
     * @return is_enabled - 是否启用
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否启用
     *
     * @param isEnabled 是否启用
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取是否通过安全检测
     *
     * @return is_checked - 是否通过安全检测
     */
    public Boolean getIsChecked() {
        return isChecked;
    }

    /**
     * 设置是否通过安全检测
     *
     * @param isChecked 是否通过安全检测
     */
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}