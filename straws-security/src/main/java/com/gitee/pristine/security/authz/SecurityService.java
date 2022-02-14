package com.gitee.pristine.security.authz;

import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.context.Subject;

/**
 * 鉴权业务接口
 * @author Pristine Xu
 * @date 2022/2/3 5:11
 * @description:
 */
public interface SecurityService {

    /**
     * 是否拥有该角色
     * @param subject
     * @param role
     * @return
     */
    boolean hasRole(Subject subject, String role);

    /**
     * 保存登录用户的信息(包含角色信息)
     * @param user
     */
    void saveTokenUserWithRoles(User user);

    /**
     * 移除用户登录信息
     * @param username
     */
    void removeTokenUserWithRoles(String username);

}
