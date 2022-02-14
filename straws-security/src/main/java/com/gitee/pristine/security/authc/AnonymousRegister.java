package com.gitee.pristine.security.authc;

import java.util.HashSet;
import java.util.Set;

/**
 * 匿名访问接口的注册器
 * @author Pristine Xu
 * @date 2022/2/5 4:05
 * @description:
 */
public class AnonymousRegister {

    private final Set<String> uris = new HashSet<>();

    public AnonymousRegister addPath(String path) {
        this.uris.add(path);
        return this;
    }

    public Set<String> getUris() {
        return uris;
    }
}
