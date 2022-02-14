package com.gitee.pristine.datasource.meta;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实例元数据
 * @author Pristine Xu
 * @date 2021/12/25 13:50
 * @description:
 */
public class SchemaMeta {

    // 数据库名称
    private String name;
    // 数据库版本
    private String version;
    // 数据库驱动
    private String driverName;
    // 驱动版本
    private String driverVersion;
    // 字符集
    private String charset;
    // 数据库的表集合
    private List<TableMeta> tables = new ArrayList<>();

    private SchemaMeta() {}

    public SchemaMeta(String name, String version, String driverName, String driverVersion, String charset, List<TableMeta> tables) {
        this.name = name;
        this.version = version;
        this.driverName = driverName;
        this.driverVersion = driverVersion;
        this.charset = charset;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public String getCharset() {
        return charset;
    }

    public List<TableMeta> getTables() {
        return tables;
    }

    public boolean containTable(String tableName) {
        if (tables.isEmpty()) {
            return false;
        }
        return tables.stream()
                .anyMatch(e -> e.getName().equals(tableName));
    }
}
