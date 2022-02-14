package com.gitee.pristine.aop.log.anno;

import com.gitee.pristine.aop.log.constant.OpType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author Pristine Xu
 * @date 2022/2/7 17:38
 * @description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLog {

    // 操作描述
    String desc() default "";

    // 指定操作者的用户名，不指定为当前登录用户的用户名
    String user() default "";

    // 操作类型， 默认为其它
    OpType type() default OpType.OTHERS;

}
