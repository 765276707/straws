package com.gitee.pristine.security.context;

import org.springframework.util.Assert;

/**
 * 安全模块上下文环境
 * @author Pristine Xu
 * @date 2022/2/3 3:52
 * @description:
 */
public class SecurityContext {

    private static final ThreadLocal<Subject> contextHolder = new ThreadLocal();

    SecurityContext() {
    }

    public static Subject getContext() {
        return contextHolder.get();
    }

    public static void setContext(Subject var1) {
        Assert.notNull(var1, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(var1);
    }

    public static void clearContext() {
        contextHolder.remove();
    }

}
