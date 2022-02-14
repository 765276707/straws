package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.common.constant.UserStatus;
import com.gitee.pristine.domain.dto.AccountLoginDTO;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.domain.vo.UserInfoVO;
import com.gitee.pristine.security.clients.Client;
import com.gitee.pristine.security.enc.PasswordEncoder;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.manager.VerifyCodeManager;
import com.gitee.pristine.web.service.SysAuthService;
import com.gitee.pristine.web.service.SysLoginLogService;
import com.gitee.pristine.web.service.SysRoleService;
import com.gitee.pristine.web.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/3 5:48
 * @description:
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysLoginLogService sysLoginLogService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private VerifyCodeManager verifyCodeManager;


    @Override
    public User executeLogin(AccountLoginDTO accountLoginDTO, Client client) {
        // 登录账号密码
        String username = accountLoginDTO.getUsername();
        String password = accountLoginDTO.getPassword();
        // 校验验证码
        boolean isLegalVerifyCode = verifyCodeManager.checkVerifyCode(accountLoginDTO.getVerifyId(), accountLoginDTO.getVerifyCode());
        if (!isLegalVerifyCode) {
            throw new ServiceException("验证码错误");
        }
        // 校验账号密码
        User sysUser = sysUserService.getByUsername(username);
        if (sysUser==null
                || !passwordEncoder.match(sysUser.getPassword(), password, sysUser.getSalt())) {
            throw new ServiceException("您的账号或密码错误");
        }
        if (!UserStatus.isActive(sysUser.getStatus())) {
            throw new ServiceException("您的账号已被禁用，请及时联系超管");
        }
        // 查询用户权限
        Set<String> roles = this.getRolesByUsername(username);
        sysUser.setRoles(roles);
        // 添加登录日志
        sysLoginLogService.saveLogWithClient(username, client);
        // 返回结果
        return sysUser;
    }

    @Override
    public UserInfoVO getUserInfo(String username) {
        // 查询用户
        User sysUser = sysUserService.getByUsername(username);
        // 查询权限
        Set<String> roles = this.getRolesByUsername(username);
        // 返回结果
        return new UserInfoVO(sysUser.getUsername(), sysUser.getAvatar(), roles);
    }

    /**
     * 获取用户角色
     * TODO 未开启权限控制之前，直接使用默认管理员角色
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username) {
        // 数据库查询角色
//        Set<String> roles = sysRoleService.getRolesByUserName(username);
//        return roles;
        // 固定角色
        Set<String> roles = new HashSet<>(1);
        roles.add("admin");
        return roles;
    }

}
