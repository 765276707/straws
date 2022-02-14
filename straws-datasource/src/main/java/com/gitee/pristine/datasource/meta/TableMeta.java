package com.gitee.pristine.datasource.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 表元数据
 * @author Pristine Xu
 * @date 2021/12/25 13:50
 * @description:
 */
public class TableMeta {

    // 数据库
    private String schema;
    // 表名
    private String name;
    // 主键列名集合
    private List<String> primaryKeys = new ArrayList<>();
    // 列集合
    private List<ColumnMeta> columns = new ArrayList<>();

    private TableMeta() {}

    public TableMeta(String schema, String name, List<String> primaryKeys, List<ColumnMeta> columns) {
        this.schema = schema;
        this.name = name;
        this.primaryKeys = primaryKeys;
        this.columns = columns;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public List<ColumnMeta> getColumns() {
        return columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnMeta getColumn(String columnName) {
        ColumnMeta columnMeta = null;
        for (ColumnMeta column : this.columns) {
            if (column.getName().equals(columnName)) {
                columnMeta = column;
                break;
            }
        }
        return columnMeta;
    }

    public String getStrColumns() {
        StringBuilder sb = new StringBuilder();
        int size = this.columns.size();
        for (int i = 0; i < size; i++) {
            if (i == size-1) {
                sb.append(this.columns.get(i).getName());
            } else {
                sb.append(this.columns.get(i).getName()).append(",");
            }
        }
        return sb.toString();
    }
}
