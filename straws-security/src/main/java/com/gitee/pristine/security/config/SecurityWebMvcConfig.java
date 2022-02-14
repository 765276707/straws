package com.gitee.pristine.security.config;

import com.gitee.pristine.security.authc.AuthcInterceptor;
import com.gitee.pristine.security.authc.TokenManager;
import com.gitee.pristine.security.authz.AuthzInterceptor;
import com.gitee.pristine.security.authz.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 拦截器的配置
 * @author Pristine Xu
 * @date 2022/2/3 2:28
 * @description:
 */
@Configuration
public class SecurityWebMvcConfig implements WebMvcConfigurer {

    // 构造注入
    private AuthcInterceptor authcInterceptor;
    private AuthzInterceptor authzInterceptor;

    /**
     * CORS过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


    /**
     * 配置鉴权拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authcInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(authzInterceptor)
                .addPathPatterns("/**");
        // 其他拦截器配置
        this.addExtendInterceptors(registry);
    }

    public void addExtendInterceptors(InterceptorRegistry registry) {
        // nothing to config
    }

    @Autowired
    public void setAuthcInterceptor(AuthcInterceptor authcInterceptor) {
        this.authcInterceptor = authcInterceptor;
    }

    @Autowired
    public void setAuthzInterceptor(AuthzInterceptor authzInterceptor) {
        this.authzInterceptor = authzInterceptor;
    }
}
