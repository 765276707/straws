package com.gitee.pristine.core.factory;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.plugins.mssql.MssqlExtractor;
import com.gitee.pristine.plugins.mssql.MssqlLoader;
import com.gitee.pristine.plugins.mysql.MysqlExtractor;
import com.gitee.pristine.plugins.mysql.MysqlLoader;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 载入器工厂
 * @author Pristine Xu
 * @date 2022/1/18 7:10
 * @description:
 */
public class LoaderFactory {

    public static Loader getInstance(Database dbType) {
        if (dbType == Database.MYSQL) {
            return new MysqlLoader();
        }
        else if (dbType == Database.SQL_SERVER) {
            return new MssqlLoader();
        }
        else {
            throw new IllegalArgumentException("can not found loader of database type, value is " + dbType.getValue());
        }
    }

}
