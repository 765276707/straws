package com.gitee.pristine.domain.condition;

import com.gitee.pristine.domain.base.TextCondition;

/**
 * @author Pristine Xu
 * @date 2022/2/1 8:14
 * @description:
 */
public class MigrateDetailCondition extends TextCondition {

    private Long migrateId;

    public Long getMigrateId() {
        return migrateId;
    }

    public void setMigrateId(Long migrateId) {
        this.migrateId = migrateId;
    }
}
