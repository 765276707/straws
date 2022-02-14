package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.RoleDTO;
import com.gitee.pristine.domain.entity.Role;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.enc.SaltGenerator;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.RoleMapper;
import com.gitee.pristine.web.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/3 6:00
 * @description:
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Set<String> getRolesByUserName(String username) {
        return roleMapper.selectRolesByUserName(username);
    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role getByUsername(String name) {
        Role condition = new Role();
        condition.setRoleName(name);
        return roleMapper.selectOne(condition);
    }

    @Override
    public List<Role> getList(TextCondition condition) {
        Example example = new Example(Role.class);
        if (!StringUtils.isEmpty(condition.getSearchText())) {
            example.createCriteria()
                    .andLike("roleName", condition.like());
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public int insertEntity(RoleDTO entity) {
        // 判断name是否存在
        Role var = this.getByUsername(entity.getRoleName());
        if (var != null) {
            throw new ServiceException("角色名已被使用");
        }
        // 赋值
        Role role = new Role();
        BeanUtils.copyProperties(entity, role);
        role.setCreateTime(new Date());
        // 插入数据库
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateEntity(RoleDTO entity) {
        // 判断记录是否存在
        Role var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("修改的角色不存在");
        }
        // 判断名称是否已被使用
        Role var2 = this.getByUsername(entity.getRoleName());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("角色名已被使用");
        }
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        // 更新数据库
        return roleMapper.updateByPrimaryKeySelective(var1);
    }

    @Override
    public int deleteById(Long id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
