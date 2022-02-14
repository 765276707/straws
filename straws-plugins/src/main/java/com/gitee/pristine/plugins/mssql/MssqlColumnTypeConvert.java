package com.gitee.pristine.plugins.mssql;

import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.type.ColumnTypeConvert;

import javax.annotation.PostConstruct;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pristine Xu
 * @date 2022/2/2 8:45
 * @description:
 */
public class MssqlColumnTypeConvert implements ColumnTypeConvert {

    private final static Map<Integer, String> MAPPING = new HashMap<>(30);

    static {
        // Number
        MAPPING.put(Types.BIT,      "BIT");
        MAPPING.put(Types.TINYINT,  "TINYINT");
        MAPPING.put(Types.SMALLINT, "SMALLINT");
        MAPPING.put(Types.INTEGER,  "INTEGER");
        MAPPING.put(Types.BIGINT,   "BIGINT");
        MAPPING.put(Types.FLOAT,    "FLOAT");
        MAPPING.put(Types.REAL,     "FLOAT");
        MAPPING.put(Types.DOUBLE,   "FLOAT");
        MAPPING.put(Types.NUMERIC,  "NUMERIC");
        MAPPING.put(Types.DECIMAL,  "DECIMAL");

        // String
        MAPPING.put(Types.CHAR,     "NCHAR");
        MAPPING.put(Types.NCHAR,    "NCHAR");
        MAPPING.put(Types.VARCHAR,  "NVARCHAR");
        MAPPING.put(Types.NVARCHAR, "NVARCHAR");
        MAPPING.put(Types.LONGVARCHAR,  "NVARCHAR(MAX)");
        MAPPING.put(Types.LONGNVARCHAR, "NVARCHAR(MAX)");

        // Date AND Time
        MAPPING.put(Types.DATE,      "DATETIME2");
        MAPPING.put(Types.TIME,      "DATETIME2");
        MAPPING.put(Types.TIMESTAMP, "DATETIME2");

        // Binary
        MAPPING.put(Types.BINARY,        "BINARY");
        MAPPING.put(Types.VARBINARY,     "VARBINARY");
        MAPPING.put(Types.LONGVARBINARY, "VARBINARY(MAX)");
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
        sql.append(type);

        // DECIMAL、NUMERIC在sqlserver中可以视为一致
        if (Types.NUMERIC==typeId || Types.DECIMAL==typeId)
        {
            sql.append("(").append(column.getLength()).append(",").append(column.getDecimalDigits()).append(")");
        }

        // String Binary
        if (Types.CHAR==typeId || Types.NCHAR==typeId || Types.VARCHAR==typeId
                || Types.NVARCHAR==typeId || Types.BINARY==typeId || Types.VARBINARY==typeId) {
            sql.append("(").append(column.getLength()).append(")");
        }

        return sql.toString();

    }

}
