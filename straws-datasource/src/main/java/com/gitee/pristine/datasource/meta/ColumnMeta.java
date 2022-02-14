package com.gitee.pristine.datasource.meta;

/**
 * 列元数据
 * @author Pristine Xu
 * @date 2021/12/25 13:51
 * @description:
 */
public class ColumnMeta {

    // 列名
    private String name;
    // 类型编号，对应的java.sql.Types的SQL类型ID
    private String typeId;
    // 类型名称，对应的java.sql.Types的SQL类型名称
    private String typeName;
    // 是否为主键
    private boolean primaryKey;
    // 是否自增
    private boolean autoIncrement;
    // 是否可为空
    private boolean nullable;
    // 长度
    private Integer length;
    // 默认值
    private String defaultValue;
    // 小数数位，精度 （numeric、decimal字段的长度为精度，如：decimal(10, 2))
    private Integer decimalDigits;
    // char类型的字符串长度
    private Integer charOctetLength;
    // 列的索引位置，从1开始
    private Integer ordinalPosition;
    // 备注信息
    private String remarks;

    private ColumnMeta() {}

    public ColumnMeta(String name, String typeId, String typeName, boolean primaryKey, boolean autoIncrement, boolean nullable, Integer length, String defaultValue, Integer decimalDigits, Integer charOctetLength, Integer ordinalPosition, String remarks) {
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.primaryKey = primaryKey;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
        this.length = length;
        this.defaultValue = defaultValue;
        this.decimalDigits = decimalDigits;
        this.charOctetLength = charOctetLength;
        this.ordinalPosition = ordinalPosition;
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Integer getLength() {
        return length;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public Integer getCharOctetLength() {
        return charOctetLength;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return "ColumnMeta{" +
                "name='" + name + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", primaryKey=" + primaryKey +
                ", autoIncrement=" + autoIncrement +
                ", nullable=" + nullable +
                ", length=" + length +
                ", defaultValue='" + defaultValue + '\'' +
                ", decimalDigits=" + decimalDigits +
                ", charOctetLength=" + charOctetLength +
                ", ordinalPosition=" + ordinalPosition +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
