package com.gitee.pristine.web.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 数据迁移进度管理器
 * @author Pristine Xu
 * @date 2022/1/26 5:27
 * @description: 利用缓存存储任务进行的进度，进度使用估算值即可
 */
@Component
public class ProgressManager {

    private Cache<String, Object> PROGRESS_CACHE;

    @PostConstruct
    public void init() {
        PROGRESS_CACHE = getCache();
    }

    /**
     * 记录任务进度的缓存
     * @return
     */
    private Cache<String, Object> getCache() {
        return Caffeine.newBuilder()
                // 写入30分后失效
                .expireAfterWrite(30, TimeUnit.MINUTES)
                // 每次访问刷新10分钟
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(10000)
                .build();
    }

    public synchronized Object getProgress(String key) {
        return PROGRESS_CACHE.getIfPresent(key);
    }

    public synchronized void putProgress(String key, Object value) {
        PROGRESS_CACHE.put(key, value);
    }

    public synchronized void removeProgress(String key) {
        PROGRESS_CACHE.invalidate(key);
    }

}
