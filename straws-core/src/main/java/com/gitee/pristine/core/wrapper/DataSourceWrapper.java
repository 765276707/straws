package com.gitee.pristine.core.wrapper;

import com.gitee.pristine.datasource.constant.Database;
import javax.sql.DataSource;

/**
 * 对 DataSource 信息的进一步封装
 * @author Pristine Xu
 * @date 2022/1/19 1:15
 * @description:
 */
public class DataSourceWrapper {

    // 数据库类型
    private Database database;
    // 数据源
    private DataSource dataSource;

    private DataSourceWrapper() {}

    public DataSourceWrapper(Database database, DataSource dataSource) {
        this.database = database;
        this.dataSource = dataSource;
    }

    public Database getDatabase() {
        return database;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public String toString() {
        return "DataSourceWrapper{" +
                "database=" + database +
                ", dataSource=" + dataSource +
                '}';
    }
}
