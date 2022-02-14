package com.gitee.pristine.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 访问该方法所需要的角色
 * @author Pristine Xu
 * @date 2022/2/3 4:50
 * @description: 目前支持单个角色，如果需要可以很方便的进行拓展
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface HasRole {

    // 角色名称
    String value() default "";

}
