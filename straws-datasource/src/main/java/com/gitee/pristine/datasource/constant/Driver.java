package com.gitee.pristine.datasource.constant;

/**
 * 数据源驱动
 * @author Pristine Xu
 * @date 2022/1/13 3:23
 * @description:
 */
public enum Driver {

    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),
    SQL_SERVER("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDrive");

    private String productName;

    private String driverClass;

    Driver(String productName, String driverClass) {
        this.productName = productName;
        this.driverClass = driverClass;
    }

    public String getProductName() {
        return productName;
    }

    public String getDriverClass() {
        return driverClass;
    }

}
