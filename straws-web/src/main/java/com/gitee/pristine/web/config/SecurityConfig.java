package com.gitee.pristine.web.config;

import com.gitee.pristine.security.authc.AnonymousRegister;
import com.gitee.pristine.security.authz.SecurityService;
import com.gitee.pristine.security.config.SecurityConfigurer;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 安全配置
 * @author Pristine Xu
 * @date 2022/2/5 3:39
 * @description:
 */
@Configuration
public class SecurityConfig extends SecurityConfigurer {

    @Resource
    private SecurityService securityService;

    @Override
    public void addAnonymousUris(AnonymousRegister register) {
        // 放行不需要身份验证的接口地址
        register.addPath("/avatars/**");
    }

    @Override
    public SecurityService securityService() {
        // 返回一个安全鉴权的业务实现类
        return securityService;
    }

}
