package com.gitee.pristine.plugins.mysql;

import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.type.ColumnTypeConvert;
import com.gitee.pristine.plugins.utils.JdbcTypesUtil;

import javax.annotation.PostConstruct;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Mysql 数据类型匹配
 * @author Pristine Xu
 * @date 2022/2/2 8:28
 * @description: 遵循 JDBC4.2规范，少数类型不支持
 */
public class MysqlColumnTypeConvert implements ColumnTypeConvert {

    private final static Map<Integer, String> MAPPING = new HashMap<>(30);

    static  {
        // Number
        MAPPING.put(Types.BIT,      "BIT");
        MAPPING.put(Types.TINYINT,  "TINYINT");
        MAPPING.put(Types.SMALLINT, "SMALLINT");
        MAPPING.put(Types.INTEGER,  "INTEGER");
        MAPPING.put(Types.BIGINT,   "BIGINT");
        MAPPING.put(Types.FLOAT,    "FLOAT");
        MAPPING.put(Types.REAL,     "FLOAT");
        MAPPING.put(Types.DOUBLE,   "DOUBLE");
        MAPPING.put(Types.NUMERIC,  "DECIMAL");
        MAPPING.put(Types.DECIMAL,  "DECIMAL");

        // String
        MAPPING.put(Types.CHAR,     "CHAR");
        MAPPING.put(Types.NCHAR,    "CHAR");
        MAPPING.put(Types.VARCHAR,  "VARCHAR");
        MAPPING.put(Types.NVARCHAR, "VARCHAR");
        MAPPING.put(Types.LONGVARCHAR,  "LONGTEXT");
        MAPPING.put(Types.LONGNVARCHAR, "LONGTEXT");

        // Date AND Time
        MAPPING.put(Types.DATE,      "DATE");
        MAPPING.put(Types.TIME,      "TIME");
        MAPPING.put(Types.TIMESTAMP, "DATETIME");

        // Binary
        MAPPING.put(Types.BINARY,        "BINARY");
        MAPPING.put(Types.VARBINARY,     "VARBINARY");
        MAPPING.put(Types.LONGVARBINARY, "LONGBLOB");
        MAPPING.put(Types.BLOB,          "BLOB");

        // Boolean
        MAPPING.put(Types.BOOLEAN,  "BIT");
    }

    @Override
    public String convert(ColumnMeta column) {
        StringBuilder sql = new StringBuilder();
        Integer typeId  = Integer.parseInt(column.getTypeId());

        // 判断类型是否受支持
        if (!MAPPING.containsKey(typeId)) {
            throw new IllegalArgumentException("不受支持的数据类型");
        }

        // 没有长度或精度的类型
        String type = MAPPING.get(typeId);

        // DECIMAL、NUMERIC在mysql中可以视为一致
        if (Types.NUMERIC==typeId || Types.DECIMAL==typeId)
        {
            sql.append(type).append("(").append(column.getLength()).append(",").append(column.getDecimalDigits()).append(")");
        }

        // Bit (1)
        else if (Types.BIT==typeId || Types.BOOLEAN==typeId) {
            sql.append(type).append("(1)");
        }

        // float real
        else if (Types.FLOAT==typeId || Types.REAL==typeId) {
            // float，REAL在mysql中都是float
            if (column.getLength()>0 && column.getLength()!=12) {
                sql.append(type).append("(").append(column.getLength()).append(",").append(column.getDecimalDigits()).append(")");
            } else {
                sql.append(type);
            }
        }

        // double
        else if (Types.DOUBLE==typeId) {
            // double 默认长度和小数精度是（22，0）
            if (column.getLength()>0 && column.getLength()!=22) {
                sql.append(type).append("(").append(column.getLength()).append(",").append(column.getDecimalDigits()).append(")");
            } else {
                sql.append(type);
            }
        }

        // String Binary
        else if (Types.CHAR==typeId || Types.NCHAR==typeId || Types.BINARY==typeId) {
            sql.append(type).append("(").append(column.getLength()).append(")");
        }

        else if (Types.VARCHAR==typeId || Types.NVARCHAR==typeId)
        {
            if (column.getLength() == Integer.MAX_VALUE) {
                sql.append("LONGTEXT");
            } else {
                sql.append("VARCHAR").append("(").append(column.getLength()).append(")");
            }
        }

        else if (Types.VARBINARY == typeId)
        {
            if (column.getLength() == Integer.MAX_VALUE) {
                sql.append("LONGBLOB");
            } else {
                sql.append("VARBINARY").append("(").append(column.getLength()).append(")");
            }
        }

        else {
            sql.append(type);
        }

        return sql.toString();
    }

}
