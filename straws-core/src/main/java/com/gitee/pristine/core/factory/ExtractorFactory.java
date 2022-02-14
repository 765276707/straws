package com.gitee.pristine.core.factory;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.plugins.mssql.MssqlExtractor;
import com.gitee.pristine.plugins.mysql.MysqlExtractor;
import com.gitee.pristine.plugins.mysql.MysqlLoader;
import com.gitee.pristine.spi.extractor.Extractor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽取器工厂
 * @author Pristine Xu
 * @date 2022/1/18 7:10
 * @description:
 */
public class ExtractorFactory {

    public static Extractor getInstance(Database dbType) {
        if (dbType == Database.MYSQL) {
            return new MysqlExtractor();
        }
        else if (dbType == Database.SQL_SERVER) {
            return new MssqlExtractor();
        }
        else {
            throw new IllegalArgumentException("can not found extractor of database type, value is " + dbType.getValue());
        }
    }

}
