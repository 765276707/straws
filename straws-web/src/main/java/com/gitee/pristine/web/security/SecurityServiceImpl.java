package com.gitee.pristine.web.security;

import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.authz.SecurityService;
import com.gitee.pristine.security.config.SecurityProperties;
import com.gitee.pristine.security.context.Subject;
import com.gitee.pristine.web.service.SysRoleService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Pristine Xu
 * @date 2022/2/3 6:47
 * @description:
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private SysRoleService sysRoleService;

    private Cache<String, Set<String>> cache;

    @PostConstruct
    public void init() {
        // 初始化缓存
        this.cache = getCache();
    }

    private Cache<String, Set<String>> getCache() {
        Integer tokenExpire = securityProperties.getToken().getExpire();
        Cache<String, Set<String>> cache = Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(tokenExpire, TimeUnit.SECONDS)
                // 每次访问刷新30分钟
                .expireAfterAccess(tokenExpire, TimeUnit.SECONDS)
                .maximumSize(100000)
                .build();
        return cache;
    }

    @Override
    public boolean hasRole(Subject subject, String role) {
        // 查询缓存
        Set<String> roles = cache.getIfPresent(subject.getUsername());
        if (roles == null) {
            roles = sysRoleService.getRolesByUserName(subject.getUsername());
        }
        return roles.contains(role);
    }

    @Override
    public void saveTokenUserWithRoles(User user) {
        cache.put(user.getUsername(), user.getRoles());
    }

    @Override
    public void removeTokenUserWithRoles(String username) {
        cache.invalidate(username);
    }

}
