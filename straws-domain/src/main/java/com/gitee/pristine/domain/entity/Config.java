package com.gitee.pristine.domain.entity;

import javax.persistence.*;

@Table(name = "sys_config")
public class Config {

    @Id
    private Long id;

    private String name;

    /**
     * 是否强制用户定期更新密码
     */
    @Column(name = "force_update_password")
    private Boolean forceUpdatePassword;

    /**
     * 密码过期使劲按间隔，单位：小时
     */
    @Column(name = "force_update_interval")
    private Integer forceUpdateInterval;

    /**
     * 是否对数据源密码在数据库存储时进行加密
     */
    @Column(name = "enc_dspwd_in_db")
    private Boolean encDspwdInDb;

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
     * 获取是否强制用户定期更新密码
     *
     * @return force_update_password - 是否强制用户定期更新密码
     */
    public Boolean getForceUpdatePassword() {
        return forceUpdatePassword;
    }

    /**
     * 设置是否强制用户定期更新密码
     *
     * @param forceUpdatePassword 是否强制用户定期更新密码
     */
    public void setForceUpdatePassword(Boolean forceUpdatePassword) {
        this.forceUpdatePassword = forceUpdatePassword;
    }

    /**
     * 获取密码过期使劲按间隔，单位：小时
     *
     * @return force_update_interval - 密码过期使劲按间隔，单位：小时
     */
    public Integer getForceUpdateInterval() {
        return forceUpdateInterval;
    }

    /**
     * 设置密码过期使劲按间隔，单位：小时
     *
     * @param forceUpdateInterval 密码过期使劲按间隔，单位：小时
     */
    public void setForceUpdateInterval(Integer forceUpdateInterval) {
        this.forceUpdateInterval = forceUpdateInterval;
    }

    /**
     * 获取是否对数据源密码在数据库存储时进行加密
     *
     * @return enc_dspwd_in_db - 是否对数据源密码在数据库存储时进行加密
     */
    public Boolean getEncDspwdInDb() {
        return encDspwdInDb;
    }

    /**
     * 设置是否对数据源密码在数据库存储时进行加密
     *
     * @param encDspwdInDb 是否对数据源密码在数据库存储时进行加密
     */
    public void setEncDspwdInDb(Boolean encDspwdInDb) {
        this.encDspwdInDb = encDspwdInDb;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", forceUpdatePassword=" + forceUpdatePassword +
                ", forceUpdateInterval=" + forceUpdateInterval +
                ", encDspwdInDb=" + encDspwdInDb +
                '}';
    }
}