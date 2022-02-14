package com.gitee.pristine.aop.encrypt.config;

import com.gitee.pristine.aop.encrypt.core.CryptManager;
import com.gitee.pristine.aop.encrypt.handler.CryptHandler;
import com.gitee.pristine.aop.encrypt.handler.RsaWithAesCryptHandler;
import com.gitee.pristine.aop.encrypt.resolver.DecEntityHandlerMethodArgumentResolver;
import com.gitee.pristine.aop.encrypt.resolver.DecParamHandlerMethodArgumentResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 加密自动装配类
 * @author Pristine Xu
 * @date 2022/2/8 12:10
 * @description:
 */
@Configuration
public class CryptAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public CryptProperties cryptProperties() {
        return new CryptProperties();
    }

    @Bean
    @ConditionalOnBean(CryptProperties.class)
    @ConditionalOnMissingBean(CryptHandler.class)
    public CryptHandler cryptHandler() {
        return new RsaWithAesCryptHandler();
    }

    @Bean
    @ConditionalOnBean(CryptHandler.class)
    public CryptManager cryptManager() {
        return new CryptManager();
    }

    @Bean
    public HandlerMethodArgumentResolver decParamHandlerMethodArgumentResolver() {
        return new DecParamHandlerMethodArgumentResolver();
    }

    @Bean
    public HandlerMethodArgumentResolver decEntityHandlerMethodArgumentResolver() {
        return new DecEntityHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(decParamHandlerMethodArgumentResolver());
        resolvers.add(decEntityHandlerMethodArgumentResolver());
    }
}
