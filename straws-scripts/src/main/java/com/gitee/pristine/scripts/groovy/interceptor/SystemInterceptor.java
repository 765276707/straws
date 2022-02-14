package com.gitee.pristine.scripts.groovy.interceptor;

import org.kohsuke.groovy.sandbox.GroovyInterceptor;

/**
 * 脚本拦截器
 * @author Pristine Xu
 * @date 2022/1/14 3:12
 * @description: 规避System的危险操作接口
 */
public class SystemInterceptor extends GroovyInterceptor {

    @Override
    public Object onStaticCall(Invoker invoker, Class receiver, String method, Object... args) throws Throwable {
        if (receiver == System.class && method.equals("exit")) {
            throw new SecurityException("Script disable invoke System.exit method.");
        }
        return super.onStaticCall(invoker, receiver, method, args);
    }


}
