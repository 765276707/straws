package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.DatasourceDTO;
import com.gitee.pristine.domain.dto.DeleteEntityDTO;
import com.gitee.pristine.domain.entity.Datasource;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:29
 * @description:
 */
public interface SysDatasourceService {

    Datasource getById(Long id);

    Datasource getByName(String name);

    List<Datasource> getList(TextCondition condition);

    int insertEntity(DatasourceDTO entity);

    int updateEntity(DatasourceDTO entity);

    int deleteById(Long id);

    int deleteAfterCheckPwd(DeleteEntityDTO deleteDTO);
}
