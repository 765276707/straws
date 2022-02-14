package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.base.BaseObject;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Pristine Xu
 * @date 2022/1/19 19:28
 * @description:
 */
public class ScriptDTO extends BaseObject {

    @NotNull(message = "编号不能为空", groups = {Update.class})
    private Long id;

    @NotEmpty(message = "脚本名称不能为空", groups = {Insert.class, Update.class})
    private String name;

    private String description;

    @NotNull(message = "是否启用不能为空", groups = {Insert.class, Update.class})
    private Boolean isEnabled;

    @NotEmpty(message = "脚本内容不能为空", groups = {Insert.class, Update.class})
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
