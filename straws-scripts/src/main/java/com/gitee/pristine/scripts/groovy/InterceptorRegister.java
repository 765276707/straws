package com.gitee.pristine.scripts.groovy;

import com.gitee.pristine.common.utils.ClassUtil;
import com.gitee.pristine.scripts.groovy.error.GroovyInterceptorRegisterError;
import org.kohsuke.groovy.sandbox.GroovyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Groovy 拦截器注册器
 * @author Pristine Xu
 * @date 2022/1/14 3:19
 * @description:
 */
public class InterceptorRegister {

    private static Logger log = LoggerFactory.getLogger(InterceptorRegister.class);

    /**
     * 注册某个路径下的所有groovy拦截器
     * @param prefix 路径前缀
     */
    public static void scanAndRegisterOfPrefix(String prefix) {
        Set<Class<? extends GroovyInterceptor>> interceptors = ClassUtil.getSubTypes(prefix, GroovyInterceptor.class);
        try {
            for (Class<? extends GroovyInterceptor> interceptor : interceptors) {
                GroovyInterceptor instance = ClassUtil.getInstance(interceptor);
                instance.register();
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("groovy interceptor register failed. cause by {}", e.getMessage());
            }
            throw new GroovyInterceptorRegisterError("groovy interceptor register failed.", e);
        }
    }

}
