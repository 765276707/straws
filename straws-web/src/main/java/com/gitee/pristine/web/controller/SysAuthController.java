package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.dto.AccountLoginDTO;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.domain.vo.UserInfoVO;
import com.gitee.pristine.security.annotation.Anonymous;
import com.gitee.pristine.security.authc.TokenManager;
import com.gitee.pristine.security.authz.SecurityService;
import com.gitee.pristine.security.clients.Client;
import com.gitee.pristine.security.clients.ClientBuilder;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.security.context.Subject;
import com.gitee.pristine.security.context.Token;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.service.SysAuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 认证控制器
 * @author Pristine Xu
 * @date 2022/2/3 5:40
 * @description:
 */
@RestController
@RequestMapping("/auth")
public class SysAuthController {

    @Resource
    private SysAuthService sysAuthService;
    @Resource
    private TokenManager tokenManager;
    @Resource
    private SecurityService securityService;


    /**
     * 用户登录
     * @param accountLoginDTO 账号密码登录参数
     * @return
     */
    @Anonymous
    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Validated AccountLoginDTO accountLoginDTO, HttpServletRequest request) {
        // 构建客户端信息
        Client client = ClientBuilder.build(request);
        // 查询并验证用户
        User user = sysAuthService.executeLogin(accountLoginDTO, client);
        // 创建令牌
        Token token = tokenManager.createToken(user);
        // 保存登录用户信息
        securityService.saveTokenUserWithRoles(user);
        // 响应结果
        return ApiResponse.success().message("登录成功").data(token);
    }

    /**
     * 获取登录用户的信息
     * @return
     */
    @GetMapping("/getInfo")
    public ApiResponse getUserInfo() {
        // 获取当前登录用户
        Subject subject = SecurityContext.getContext();
        // TODO
        if (subject == null) {
            throw new ServiceException("账号异常，请稍后再试");
        }
        String username = subject.getUsername();
        // 查询用户信息
        UserInfoVO userInfo = sysAuthService.getUserInfo(username);
        // 返回结果
        return ApiResponse.success().data(userInfo);
    }

    /**
     * 注销登录
     * @return
     */
    @GetMapping("/logout")
    public ApiResponse logout() {
        // 获取当前登录用户
        Subject subject = SecurityContext.getContext();
        if (subject != null) {
            // 移除用户登录信息
            securityService.removeTokenUserWithRoles(subject.getUsername());
        }
        // 返回结果
        return ApiResponse.success().message("登出成功");
    }
}
