package com.gitee.pristine.domain.vo;

/**
 * @author Pristine Xu
 * @date 2022/2/4 11:13
 * @description:
 */
public class VerifyCodeVO {

    // 验证码编号
    private String verifyId;

    // 验证码
    private String verifyCode;

    public VerifyCodeVO() {
    }

    public VerifyCodeVO(String verifyId, String verifyCode) {
        this.verifyId = verifyId;
        this.verifyCode = verifyCode;
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
