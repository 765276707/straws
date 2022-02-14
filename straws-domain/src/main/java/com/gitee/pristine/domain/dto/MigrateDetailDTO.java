package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.base.BaseObject;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * @author Pristine Xu
 * @date 2022/1/20 1:19
 * @description:
 */
public class MigrateDetailDTO extends BaseObject {

    @NotNull(message = "映射编号不能为空", groups = {Update.class})
    private Long id;

    @NotNull(message = "所属迁移任务编号不能为空", groups = {Insert.class, Update.class})
    private Long migrateId;

    @NotBlank(message = "源头表的表名不能为空", groups = {Insert.class, Update.class})
    private String originTableName;

    @NotBlank(message = "目标表的表名不能为空", groups = {Insert.class, Update.class})
    private String targetTableName;

    private String transformers;

    @NotNull(message = "是否创建表不能为空", groups = {Insert.class, Update.class})
    private Boolean isCreateTable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMigrateId() {
        return migrateId;
    }

    public void setMigrateId(Long migrateId) {
        this.migrateId = migrateId;
    }

    public String getOriginTableName() {
        return originTableName;
    }

    public void setOriginTableName(String originTableName) {
        this.originTableName = originTableName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public Boolean getIsCreateTable() {
        return isCreateTable;
    }

    public void setIsCreateTable(Boolean createTable) {
        isCreateTable = createTable;
    }

    public String getTransformers() {
        return transformers;
    }

    public void setTransformers(String transformers) {
        this.transformers = transformers;
    }
}
