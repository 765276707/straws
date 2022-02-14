package com.gitee.pristine.aop.log.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pristine Xu
 * @date 2022/2/8 6:47
 * @description:
 */
@Configuration
public class AopLogConfig {

    @Bean
    public LoggerAspectj loggerAspectj() {
        return new LoggerAspectj();
    }

}
