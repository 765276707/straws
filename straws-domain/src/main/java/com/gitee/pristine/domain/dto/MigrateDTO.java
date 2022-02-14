package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.base.BaseObject;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 数据结构迁移数据传输模型
 * @author Pristine Xu
 * @date 2022/1/19 18:59
 * @description:
 */
public class MigrateDTO extends BaseObject {

    @NotNull(message = "编号不能为空", groups = {Update.class})
    private Long id;

    @NotEmpty(message = "迁移任务名称不能为空", groups = {Insert.class, Update.class})
    private String name;

    @NotNull(message = "原始库不能为空", groups = {Insert.class, Update.class})
    private Long originDsId;

    @NotNull(message = "目标库不能为空", groups = {Insert.class, Update.class})
    private Long targetDsId;

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

    public Long getOriginDsId() {
        return originDsId;
    }

    public void setOriginDsId(Long originDsId) {
        this.originDsId = originDsId;
    }

    public Long getTargetDsId() {
        return targetDsId;
    }

    public void setTargetDsId(Long targetDsId) {
        this.targetDsId = targetDsId;
    }
}
