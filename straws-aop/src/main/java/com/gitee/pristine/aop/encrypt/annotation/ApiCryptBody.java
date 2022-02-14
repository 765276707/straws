package com.gitee.pristine.aop.encrypt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 请求体加解密双向注解
 * @author xzbcode
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface ApiCryptBody {
}
