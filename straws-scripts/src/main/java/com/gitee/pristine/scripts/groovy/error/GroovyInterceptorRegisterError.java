package com.gitee.pristine.scripts.groovy.error;

import com.gitee.pristine.common.ex.StrawsError;

/**
 * 脚本拦截器注册错误
 * @author Pristine Xu
 * @date 2022/1/14 3:25
 * @description: 本错误为编译时异常，如出现系统将无法启动
 */
public class GroovyInterceptorRegisterError extends StrawsError {

    public GroovyInterceptorRegisterError(String message, Throwable cause) {
        super(message, cause);
    }
}
