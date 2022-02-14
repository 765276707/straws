package com.gitee.pristine.datasource.constant;

/**
 * @author Pristine Xu
 * @date 2022/1/13 3:15
 * @description:
 */
public enum Database {

    MYSQL("1"),

    SQL_SERVER("2");

    private String type;

    Database(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }

}
