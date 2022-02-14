package com.gitee.pristine.plugins.mssql;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.datasource.constant.Driver;
import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.type.ColumnTypeConvert;
import com.gitee.pristine.plugins.common.CommonJdbcDialect;

import java.util.List;

/**
 * sql server 数据库方言实现
 * @author Pristine Xu
 * @date 2022/1/14 2:24
 * @description:
 */
public class MssqlDialect extends CommonJdbcDialect {

    @Override
    public Database getDatabase() {
        return Database.SQL_SERVER;
    }

    @Override
    public String getDriverClass() {
        return Driver.SQL_SERVER.getDriverClass();
    }

    @Override
    public ColumnTypeConvert getColumnTypeConvert() {
        return new MssqlColumnTypeConvert();
    }

    @Override
    public String createTable(TableMeta tableMeta) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE ").append(tableMeta.getName());
        ddl.append("(");

        List<ColumnMeta> columns = tableMeta.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            ColumnMeta column = columns.get(i);
            // 拼接ddl属性
            ddl.append(this.createColumns(column));
            if (!(i==columns.size()-1)) {
                ddl.append(",");
            }
        }
        ddl.append(");");
        // 列注释
        String columnComment = this.createColumnsComment(tableMeta);
        ddl.append(columnComment).append(" ");
        // 主键
        String pks = this.createPrimaryKey(tableMeta);
        ddl.append(pks).append(" ");
        return ddl.toString();
    }

    /**
     * 生成列的建表sql
     * @param column
     * @return 返回示例：ID INT NOT NULL AUTO_INCREMENT COMMENT ''
     */
    public String createColumns(ColumnMeta column) {
        ColumnTypeConvert typeConvert = getColumnTypeConvert();
        StringBuilder ddl = new StringBuilder();
        // 列名
        ddl.append("[").append(column.getName()).append("]");
        // 列类型
        ddl.append(" ").append(typeConvert.convert(column));
        // 是否为空，不为空的默认值
        if (!column.isNullable()) {
            ddl.append(" ").append("NOT NULL");
        } else {
            if (column.getDefaultValue() == null) {
                ddl.append(" ").append("DEFAULT NULL");
            } else {
                ddl.append(" ").append("DEFAULT").append(" '").append(column.getDefaultValue()).append("'");
            }
        }
        // 是否自增
        if (column.isAutoIncrement()) {
            ddl.append(" ").append("IDENTITY(1,1)");
        }
        return ddl.toString();
    }

    /**
     * 拼接主键
     * @param tableMeta
     * @return
     */
    private String createPrimaryKey(TableMeta tableMeta) {
        StringBuilder pks = new StringBuilder();
        List<String> primaryKeys = tableMeta.getPrimaryKeys();
        pks.append( "ALTER TABLE [dbo].[").append(tableMeta.getName()).append("] ADD PRIMARY KEY (");
        for (int i = 0; i < primaryKeys.size(); i++) {
            if (i == primaryKeys.size()-1) {
                pks.append("[").append(primaryKeys.get(i)).append("]");
            } else {
                pks.append("[").append(primaryKeys.get(i)).append("], ");
            }
        }
        pks.append(");");
        return pks.toString();
    }

    /**
     * 拼接列注释
     * @param tableMeta
     * @return
     */
    private String createColumnsComment(TableMeta tableMeta) {
        StringBuilder comment = new StringBuilder();
        List<ColumnMeta> columns = tableMeta.getColumns();
        for (ColumnMeta columnMeta : columns) {
            String remarks = columnMeta.getRemarks();
            if (remarks != null && !remarks.equals("")) {
                comment.append(" EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'").append(remarks).append("' ")
                        .append(", @level0type = 'SCHEMA', @level0name = N'dbo' ")
                        .append(", @level1type = 'TABLE', @level1name = N'").append(tableMeta.getName()).append("' ")
                        .append(", @level2type = 'COLUMN', @level2name = N'").append(columnMeta.getName()).append("' ")
                        .append(";");
            }
        }
        return comment.toString();
    }
}
