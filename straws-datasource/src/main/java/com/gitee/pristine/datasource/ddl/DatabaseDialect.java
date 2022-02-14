package com.gitee.pristine.datasource.ddl;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.datasource.type.ColumnTypeConvert;

/**
 * 数据库方言
 * @author Pristine Xu
 * @date 2022/1/14 1:22
 * @description: 不同的数据库接入本框架都需要实现自己的数据库方言
 */
public interface DatabaseDialect {

    // 获取数据库类型
    public Database getDatabase();

    // 获取数据库驱动
    public String getDriverClass();

    // 获取数据类型转换器
    public ColumnTypeConvert getColumnTypeConvert();

    // 获取建表语句
    public String createTable(TableMeta tableMeta);

    // 获取删表语句
    public String dropTable(String tableName);

    // 获取清空表数据语句
    public String clearTable(String tableName);

    // 生成查询语句
    public String generateQuerySql(final String tableName, final String columns, final String splitPk, final SplitPkRange splitPkRange);

    // 生成插入语句
    public String generateInsertSql(final String tableName, final String columns);

    // 生成更新语句
    public String generateUpdateSql(final String tableName, final String columns, final String splitPk);

    // 生成删除语句
    public String generateDeleteSql(final String tableName, String splitPk);
}
