package com.gitee.pristine.aop.log.constant;

/**
 * 操作类型
 * @author Pristine Xu
 * @date 2022/2/7 17:35
 * @description: 对应于日志操作类型
 */
public enum OpType {

    INSERT("新增", "#67C23A"),
    UPDATE("更新", "#409EFF"),
    DELETE("删除", "#F56C6C"),
    OPERATE("操作", "#409EFF"),
    OTHERS("未定义", "#909399");

    private String type;
    private String color;

    OpType(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public String getValue() {
        return this.type;
    }

    public String getColor() {
        return color;
    }
}
