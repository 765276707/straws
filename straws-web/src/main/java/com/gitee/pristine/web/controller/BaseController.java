package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.common.utils.SpringUtil;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.security.enc.PasswordEncoder;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.service.SysUserService;

/**
 * 基础控制器类
 * @author Pristine Xu
 * @date 2022/1/19 6:16
 * @description: 提供公共方法
 */
public abstract class BaseController {

    protected final static ApiResponse INSERT_SUCCESS = ApiResponse.success().message("添加成功");

    protected final static ApiResponse INSERT_FAILURE = ApiResponse.failure().message("添加失败");

    protected final static ApiResponse UPDATE_SUCCESS = ApiResponse.success().message("更新成功");

    protected final static ApiResponse UPDATE_FAILURE = ApiResponse.failure().message("更新失败");

    protected final static ApiResponse DELETE_SUCCESS = ApiResponse.success().message("删除成功");

    protected final static ApiResponse DELETE_FAILURE = ApiResponse.failure().message("删除失败");

    /**
     * 检查添加结果
     * @param rows
     * @return
     */
    protected static ApiResponse checkInsertResult(int rows) {
        return rows > 0 ? INSERT_SUCCESS : INSERT_FAILURE;
    }

    /**
     * 检查更新结果
     * @param rows
     * @return
     */
    protected static ApiResponse checkUpdateResult(int rows) {
        return rows > 0 ? UPDATE_SUCCESS : UPDATE_FAILURE;
    }

    /**
     * 检查删除结果
     * @param rows
     * @return
     */
    protected static ApiResponse checkDeleteResult(int rows) {
        return rows > 0 ? DELETE_SUCCESS : DELETE_FAILURE;
    }

    /**
     * 检测当前操作的用户的密码是否匹配
     * 常用于在操作之前对登录用户的密码进行进一步的验证
     * @param password
     */
    protected void checkCurrentLoginUserPassword(String password) {
        String username = SecurityContext.getContext().getUsername();
        SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);
        PasswordEncoder passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
        User loginUser = sysUserService.getByUsername(username);
        if (loginUser == null) {
            throw new ServiceException("身份验证失败，非法操作已拒绝");
        }
        if (!passwordEncoder.match(loginUser.getPassword(), password, loginUser.getSalt())) {
            throw new ServiceException("身份验证失败，非法操作已拒绝");
        }
    }
}

