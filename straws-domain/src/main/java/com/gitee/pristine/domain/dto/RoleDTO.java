package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Pristine Xu
 * @date 2022/2/5 1:16
 * @description:
 */
public class RoleDTO {

    @NotNull(message = "角色编号不能为空", groups = {Update.class})
    private Long id;

    @NotEmpty(message = "角色名称不能为空", groups = {Insert.class, Update.class})
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
