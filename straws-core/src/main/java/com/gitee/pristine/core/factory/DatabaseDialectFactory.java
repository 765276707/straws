package com.gitee.pristine.core.factory;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.plugins.mssql.MssqlDialect;
import com.gitee.pristine.plugins.mssql.MssqlExtractor;
import com.gitee.pristine.plugins.mysql.MysqlDialect;
import com.gitee.pristine.plugins.mysql.MysqlExtractor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库字典工厂
 * @author Pristine Xu
 * @date 2022/1/19 0:53
 * @description:
 */
public class DatabaseDialectFactory {


    public static DatabaseDialect getInstance(Database dbType) {
        if (dbType == Database.MYSQL) {
            return new MysqlDialect();
        }
        else if (dbType == Database.SQL_SERVER) {
            return new MssqlDialect();
        }
        else {
            throw new IllegalArgumentException("can not found dialect of database type, value is " + dbType.getValue());
        }
    }

}
