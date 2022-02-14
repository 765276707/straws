package com.gitee.pristine.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 * @author Pristine Xu
 * @date 2022/1/17 13:02
 * @description:
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    public static <T> T getBean(String beanName) {
        checkContextHadInjected();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<?> beanClass) {
        checkContextHadInjected();
        return (T) applicationContext.getBean(beanClass);
    }

    /**
     * 检测 ApplicationContext 是否已经注入
     */
    private static void checkContextHadInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext Not injected");
        }
    }
}
