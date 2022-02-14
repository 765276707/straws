package com.gitee.pristine.domain.dto;

/**
 * 在操作之前进行确认
 * @author Pristine Xu
 * @date 2022/2/4 12:29
 * @description:
 */
public class CheckBeforeOperateDTO {

    private String password;

    private Long id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
