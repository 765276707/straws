package com.gitee.pristine.web.mapper;

import com.gitee.pristine.domain.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends Mapper<Role> {

    /**
     * 查询用户的角色
     * @param username 用户名
     * @return
     */
    Set<String> selectRolesByUserName(@Param("username") String username);

}