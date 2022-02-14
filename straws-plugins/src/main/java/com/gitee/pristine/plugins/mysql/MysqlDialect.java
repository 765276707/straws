package com.gitee.pristine.plugins.mysql;

import com.gitee.pristine.datasource.constant.Database;
import com.gitee.pristine.datasource.constant.Driver;
import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.type.ColumnTypeConvert;
import com.gitee.pristine.plugins.common.CommonJdbcDialect;

import java.util.List;

/**
 * Mysql数据库方言的实现
 * @author Pristine Xu
 * @date 2022/1/14 1:40
 * @description: 尚未对索引实现同步，待后续完善
 */
public class MysqlDialect extends CommonJdbcDialect {

    @Override
    public Database getDatabase() {
        return Database.MYSQL;
    }

    @Override
    public String getDriverClass() {
        return Driver.MYSQL.getDriverClass();
    }

    @Override
    public ColumnTypeConvert getColumnTypeConvert() {
        return new MysqlColumnTypeConvert();
    }

    @Override
    public String createTable(TableMeta tableMeta) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE `").append(tableMeta.getName()).append("`");
        ddl.append("(");

        List<ColumnMeta> columns = tableMeta.getColumns();
        List<String> pks = tableMeta.getPrimaryKeys();
        for (int i = 0; i < columns.size(); i++) {
            ColumnMeta column = columns.get(i);
            // 拼接ddl属性
            ddl.append(this.createColumns(column));
            if (!(i==columns.size()-1)) {
                ddl.append(",");
            }
        }

        if (!pks.isEmpty()) {
            ddl.append(", PRIMARY KEY (");
            for (int i = 0; i < pks.size(); i++) {
                if (i == pks.size()-1) {
                    ddl.append("`").append(pks.get(i)).append("`");
                } else {
                    ddl.append("`").append(pks.get(i)).append("`").append(",");
                }
            }
            ddl.append(")");
        }

        ddl.append(")");
        ddl.append(" ENGINE=InnoDB DEFAULT CHARSET=utf8");
        return ddl.toString();
    }

    /**
     * 生成列的建表sql
     * @param column
     * @return
     */
    public String createColumns(ColumnMeta column) {
        ColumnTypeConvert typeConvert = this.getColumnTypeConvert();
        StringBuilder ddl = new StringBuilder();
        // 列名
        ddl.append("`").append(column.getName()).append("`");
        // 列类型
        ddl.append(" ").append(typeConvert.convert(column));
        // 是否为空，不为空的默认值
        if (!column.isNullable()) {
            ddl.append(" ").append("NOT NULL");
        } else {
            String defaultValue = column.getDefaultValue();
            if (defaultValue == null || defaultValue.equalsIgnoreCase("NULL")) {
                ddl.append(" ").append("DEFAULT NULL");
            } else {
                // 判断需不需要加单引号， 兼容SQLServer
                if (defaultValue.startsWith("(") && defaultValue.endsWith(")")) {
                    ddl.append(" ").append("DEFAULT ").append(
                            defaultValue
                                    .replace("(", "")
                                    .replace(")", "")
                    );
                }
                else if (defaultValue.startsWith("'") && defaultValue.endsWith("'")) {
                    ddl.append(" ").append("DEFAULT ").append(defaultValue);
                }
                else {
                    ddl.append(" ").append("DEFAULT ").append("'").append(defaultValue).append("'");
                }
            }
        }
        // 是否自增
        if (column.isAutoIncrement()) {
            ddl.append(" ").append("AUTO_INCREMENT");
        }
        // 注释
        String remarks = column.getRemarks();
        if (remarks!=null && !"".equals(remarks)) {
            ddl.append(" ").append("COMMENT '").append(remarks).append("'");
        }
        return ddl.toString();
    }
}
