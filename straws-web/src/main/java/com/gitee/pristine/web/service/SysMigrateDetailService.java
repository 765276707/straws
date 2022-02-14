package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.condition.MigrateDetailCondition;
import com.gitee.pristine.domain.dto.MigrateDetailDTO;
import com.gitee.pristine.domain.entity.MigrateDetail;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:29
 * @description:
 */
public interface SysMigrateDetailService {

    MigrateDetail getById(Long id);

    List<MigrateDetail> getList(MigrateDetailCondition condition);

    int insertEntity(MigrateDetailDTO entity);

    int updateEntity(MigrateDetailDTO entity);

    int deleteById(Long id);

    void createDetails(Long migrateId);

    void clearDetails(Long migrateId);

    void migrateTable(Long migrateId);

    void migrateData(Long migrateId);
}
