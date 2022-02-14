package com.gitee.pristine.security.utils;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;

/**
 * 注解工具类
 * @author Pristine Xu
 * @date 2022/2/3 4:46
 * @description:
 */
public class AnnotationUtil {

    /**
     * 获取方法上的注解
     * @param handler
     * @param annotationType
     * @param <A>
     * @return
     */
    public static <A extends Annotation> A getMethodAnnotation(Object handler, Class<A> annotationType) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod =  (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(annotationType)) {
                return handlerMethod.getMethodAnnotation(annotationType);
            }
        }
        return null;
    }

    /**
     * 方法上是否有该注解
     * @param handler
     * @param annotationType
     * @param <A>
     * @return
     */
    public static <A extends Annotation> boolean hasMethodAnnotation(Object handler, Class<A> annotationType) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod =  (HandlerMethod) handler;
            return handlerMethod.hasMethodAnnotation(annotationType);
        }
        return false;
    }

}
