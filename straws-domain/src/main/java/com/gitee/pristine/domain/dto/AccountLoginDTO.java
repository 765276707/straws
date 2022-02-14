package com.gitee.pristine.domain.dto;


import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Pristine Xu
 * @date 2022/2/3 5:43
 * @description:
 */
public class AccountLoginDTO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码编号不能为空")
    private String verifyId;

    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
