package com.gitee.pristine.domain.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/3 10:45
 * @description:
 */
public class UserInfoVO {

    private String username;

    private String avatar;

    private Set<String> roles = new HashSet<>();

    public UserInfoVO() {
    }

    public UserInfoVO(String username, String avatar, Set<String> roles) {
        this.username = username;
        this.avatar = avatar;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
