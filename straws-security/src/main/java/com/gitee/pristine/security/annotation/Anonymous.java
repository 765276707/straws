package com.gitee.pristine.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 允许匿名访问接口
 * @author Pristine Xu
 * @date 2022/2/3 8:17
 * @description:
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface Anonymous {

}
