package com.gitee.pristine.web.manager;

import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.web.ex.DataSourceNotFoundException;
import com.gitee.pristine.web.service.SysDatasourceService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源管理器
 * @author Pristine Xu
 * @date 2022/1/22 3:38
 * @description:
 */
@Component
public class DataSourceManager {

    // 存放已创建的数据源
    private final static Map<Long, DataSourceWrapper> CACHE = new ConcurrentHashMap<>();

    @Resource
    private SysDatasourceService sysDatasourceService;

    public synchronized DataSourceWrapper getDataSource(Long dsId) {
        DataSourceWrapper dsw = null;
        if (!CACHE.containsKey(dsId))
        {
            Datasource dsEntity = sysDatasourceService.getById(dsId);
            if (dsEntity == null) {
                throw new DataSourceNotFoundException("数据源不存在");
            }
            HikariDataSource dataSource = createDataSource(dsEntity);
            Database database = getDatabaseType(dsEntity.getTypeId());
            dsw = new DataSourceWrapper(database, dataSource);
            CACHE.put(dsId, dsw);
        }
        else
        {
            dsw = CACHE.get(dsId);
        }
        return dsw;
    }

    public void initDataSourceWithEntity(Datasource dsEntity) {
        if (!CACHE.containsKey(dsEntity.getId())) {
            HikariDataSource dataSource = createDataSource(dsEntity);
            Database database = getDatabaseType(dsEntity.getTypeId());
            CACHE.put(dsEntity.getId(), new DataSourceWrapper(database, dataSource));
        }
    }

    private HikariDataSource createDataSource(Datasource datasource) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(datasource.getDriverClassName());
        dataSource.setUsername(datasource.getUsername());
        dataSource.setPassword(datasource.getPassword());
        dataSource.setJdbcUrl(datasource.getJdbcUrl());
        return dataSource;
    }

    private Database getDatabaseType(String dsTypeId) {
        if (Database.MYSQL.getValue().equals(dsTypeId)) {
            return Database.MYSQL;
        }
        if (Database.SQL_SERVER.getValue().equals(dsTypeId)) {
            return Database.SQL_SERVER;
        }
        else {
            throw new IllegalArgumentException("不存在的数据库类型");
        }
    }


}
