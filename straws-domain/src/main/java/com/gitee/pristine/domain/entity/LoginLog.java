package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_login_log")
public class LoginLog {
    @Id
    private Long id;

    @Column(name = "login_user")
    private String loginUser;

    @Column(name = "login_mode")
    private String loginMode;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_os")
    private String loginOs;

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
     * @return login_user
     */
    public String getLoginUser() {
        return loginUser;
    }

    /**
     * @param loginUser
     */
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser == null ? null : loginUser.trim();
    }

    /**
     * @return login_mode
     */
    public String getLoginMode() {
        return loginMode;
    }

    /**
     * @param loginMode
     */
    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode == null ? null : loginMode.trim();
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return login_ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * @return login_os
     */
    public String getLoginOs() {
        return loginOs;
    }

    /**
     * @param loginOs
     */
    public void setLoginOs(String loginOs) {
        this.loginOs = loginOs == null ? null : loginOs.trim();
    }
}