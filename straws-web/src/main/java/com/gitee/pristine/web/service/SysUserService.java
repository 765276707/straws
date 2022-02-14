package com.gitee.pristine.web.service;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.ModifyPasswordDTO;
import com.gitee.pristine.domain.dto.UserDTO;
import com.gitee.pristine.domain.entity.User;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/3 5:49
 * @description:
 */
public interface SysUserService {

    User getById(Long id);

    User getByUsername(String username);

    List<User> getList(TextCondition condition);

    int insertEntity(UserDTO entity);

    int updateEntity(UserDTO entity);

    int modifyPassword(String username, ModifyPasswordDTO modifyPasswordDTO);

    int resetPassword(Long userId);

    int deleteById(Long id);
}
