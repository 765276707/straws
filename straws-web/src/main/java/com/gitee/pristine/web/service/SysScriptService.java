package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.ScriptDTO;
import com.gitee.pristine.domain.entity.Script;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:30
 * @description:
 */
public interface SysScriptService {
    
    Script getById(Long id);

    Script getByName(String name);

    List<Script> getList(TextCondition condition);

    int insertEntity(ScriptDTO entity);

    int updateEntity(ScriptDTO entity);

    int deleteById(Long id);
}
