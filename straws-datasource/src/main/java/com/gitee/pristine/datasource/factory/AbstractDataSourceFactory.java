package com.gitee.pristine.datasource.factory;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象数据源工厂
 * @author Pristine Xu
 * @date 2022/1/13 19:38
 * @description: 所有的数据源都从此处获取
 */
public abstract class AbstractDataSourceFactory {

    // 存放已创建的数据源
    private final static Map<Long, HikariDataSource> dataSourceCache = new ConcurrentHashMap<>();

    public static void putInCache(Long dsId, HikariDataSource dataSource) {
        dataSourceCache.put(dsId, dataSource);
    }

    public static HikariDataSource getFromCache(Long dsId) {
        return dataSourceCache.get(dsId);
    }

    public static boolean containKey(Long dsId) {
        return dataSourceCache.containsKey(dsId);
    }

    public static void removeFromCache(Long dsId) {
        dataSourceCache.remove(dsId);
    }

}
