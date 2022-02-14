package com.gitee.pristine.aop.encrypt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解析基于application/x-www-form-urlencoded编码的GET请求参数
 * @author xzbcode
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptParam {


}
