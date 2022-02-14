package com.gitee.pristine.domain.vo;

/**
 * @author Pristine Xu
 * @date 2022/1/27 0:25
 * @description:
 */
public class TableVO {

    private String schemaName;

    private String tableName;

    public TableVO(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
