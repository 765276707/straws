package com.gitee.pristine.datasource.split;

/**
 * 分割列的类型
 * @author Pristine Xu
 * @date 2022/1/30 13:02
 * @description: 支持 int、long
 */
public enum SplitPkType {

    INT(1),
    LONG(2);

    private Integer type;

    SplitPkType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
