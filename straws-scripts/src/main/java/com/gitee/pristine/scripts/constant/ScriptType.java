package com.gitee.pristine.scripts.constant;

/**
 * 脚本类型
 * @author Pristine Xu
 * @date 2022/1/14 3:01
 * @description: 目前仅支持 groovy 脚本
 */
public enum ScriptType {

    GROOVY("groovy");

    private String type;

    ScriptType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }
}
