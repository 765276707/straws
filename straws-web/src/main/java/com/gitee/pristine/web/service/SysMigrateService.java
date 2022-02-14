package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.MigrateDTO;
import com.gitee.pristine.domain.entity.Migrate;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:29
 * @description:
 */
public interface SysMigrateService {

    Migrate getById(Long id);

    Migrate getByName(String name);

    List<Migrate> getList(TextCondition condition);

    int insertEntity(MigrateDTO entity);

    int updateEntity(MigrateDTO entity);

    int deleteById(Long id);
}
