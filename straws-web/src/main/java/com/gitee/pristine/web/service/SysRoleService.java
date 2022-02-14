package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.RoleDTO;
import com.gitee.pristine.domain.entity.Role;
import com.gitee.pristine.domain.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/3 6:00
 * @description:
 */
public interface SysRoleService {

    /**
     * 获取用户的角色
     * @param username
     * @return
     */
    Set<String> getRolesByUserName(String username);

    Role getById(Long id);

    Role getByUsername(String name);

    List<Role> getList(TextCondition condition);

    int insertEntity(RoleDTO entity);

    int updateEntity(RoleDTO entity);

    int deleteById(Long id);

}
