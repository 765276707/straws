package com.gitee.pristine.plugins.common;

import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.plugins.utils.CommonSqlUtil;

/**
 * @author Pristine Xu
 * @date 2022/1/19 3:12
 * @description:
 */
public abstract class CommonJdbcDialect implements DatabaseDialect {

    @Override
    public String dropTable(String tableName) {
        return "drop table " + tableName;
    }

    @Override
    public String clearTable(String tableName) {
        return "delete from " + tableName;
    }

    @Override
    public String generateQuerySql(String tableName, String columns, String splitPk, SplitPkRange splitPkRange) {
        if (splitPk==null || splitPk.equals(""))
        {
            // splitPk未设置时，直接查询全表
            return "select "+ columns +" from " + tableName;
        }
        else
        {
            // 根据splitPk和splitPkRange查询数据
            return  "select "+ columns +" from " + tableName + " where " + CommonSqlUtil.createRange(splitPk, splitPkRange);
        }
    }

    @Override
    public String generateInsertSql(String tableName, String columns) {
        return "insert into " + tableName + "(" + columns
                + ") values ("+ CommonSqlUtil.createInsertSqlParamsPlaceholder(columns) +")";
    }

    @Override
    public String generateUpdateSql(String tableName, String columns, String splitPk) {
        // 生成更新sql TODO 此处需要优化成 where pk1=? and pk2=? ...
        return "update " + tableName + " set " + CommonSqlUtil.createUpdateSqlParamsPlaceholder(columns, splitPk)
                + " where " + splitPk + "=?" ;
    }

    @Override
    public String generateDeleteSql(String tableName, String splitPk) {
        // TODO 此处需要优化成 where pk1=? and pk2=? ...
        return "delete from " + tableName + " where " + splitPk + "=?";
    }

}
