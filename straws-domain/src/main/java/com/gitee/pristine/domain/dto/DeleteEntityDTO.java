package com.gitee.pristine.domain.dto;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 删除数据传输模型
 * @author Pristine Xu
 * @date 2022/1/23 6:54
 * @description: 模型是通用的，实体类中主键必须是long类型的id
 */
public class DeleteEntityDTO {

    // 用户密码
    @NotBlank(message = "确认删除提供的操作用户的密码不能为空")
    private String password;

    // 要删除的实体类编号
    @NotNull(message = "删除的对象编号不能为空")
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
