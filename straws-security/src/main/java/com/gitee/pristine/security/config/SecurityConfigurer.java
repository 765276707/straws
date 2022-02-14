package com.gitee.pristine.security.config;

import com.gitee.pristine.security.authc.AnonymousRegister;
import com.gitee.pristine.security.authc.AuthcInterceptor;
import com.gitee.pristine.security.authc.TokenManager;
import com.gitee.pristine.security.authz.AuthzInterceptor;
import com.gitee.pristine.security.authz.SecurityService;
import org.springframework.context.annotation.Bean;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pristine Xu
 * @date 2022/2/5 3:44
 * @description:
 */
public abstract class SecurityConfigurer {

    @Bean
    public SecurityProperties securityProperties() {
        SecurityProperties securityProperties = new SecurityProperties();
        AnonymousRegister anonymousRegister = new AnonymousRegister();
        anonymousRegister.addPath("/static/**")
                    .addPath("/public/**")
                    .addPath("/resources/**")
                    .addPath("/META-INF/resources/**");
        addAnonymousUris(anonymousRegister);
        securityProperties.getToken()
                .setAnonymousUris(anonymousRegister.getUris());
        return securityProperties;
    }

    @Bean
    public TokenManager tokenManager() {
        return new TokenManager();
    }

    @Bean
    public AuthcInterceptor authcInterceptor() {
        return new AuthcInterceptor(securityProperties());
    }

    @Bean
    public AuthzInterceptor authzInterceptor() {
        return new AuthzInterceptor(securityService());
    }


    /**
     * 添加允许匿名访问的接口URI
     * 1.数据接口直接采用 @Anonymous 注解更为优雅
     * 2.针对非数据接口可以使用本配置方法，默认放行SpringBoot静态资源文件路径
     * classpath:/static
     * classpath:/public
     * classpath:/resources
     * classpath:/META-INF/resources
     * @param register
     */
    public void addAnonymousUris(AnonymousRegister register) {
        // 你可以在这里做一些配置
    }


    /**
     * 配置一个 SecurityService，必须配置
     * @return
     */
    public abstract SecurityService securityService();

}
