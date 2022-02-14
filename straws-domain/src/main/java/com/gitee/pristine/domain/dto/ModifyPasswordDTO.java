package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.jsr303.annotation.FieldValueEqualsTrue;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 更改用户密码
 * @author Pristine Xu
 * @date 2022/2/4 4:31
 * @description:
 */
//@FieldValueEqualsTrue(sourceField = "newPassword", targetField = "repPassword", message = "新密码与确认密码不一致")
public class ModifyPasswordDTO {

    @NotEmpty(message = "原始密码不能为空")
    private String oldPassword;

    @NotEmpty(message = "新的密码不能为空")
    private String newPassword;

    @NotEmpty(message = "确认密码不能为空")
    private String repPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }
}
