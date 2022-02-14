package com.gitee.pristine.datasource.split;

/**
 * 分割字段，目前只支持 int、long 类型
 * @author Pristine Xu
 * @date 2022/1/13 5:25
 * @description: 要求该字段必须时自增字段
 */
public class SplitPk {

    // 必须时自增字段
    private Number pk;

    // 字段类型, 1->int，2->long
    private Integer type = 2;

    public SplitPk(Number pk, Integer type) {
        this.pk = pk;
        this.type = type;
    }

    public static SplitPk copy(SplitPk splitPk) {
        // TODO 这里的类型 先采用  long
        return new SplitPk(splitPk.getPk(), 2);
    }

    private long getLongValue() {
        return pk==null ? 0:pk.longValue();
    }

    private int getIntValue() {
        return pk==null ? 0:pk.intValue();
    }

    public Number getPk() {
        return pk;
    }

    public Integer getType() {
        return type;
    }

    public SplitPk plus(int var) {
        if (this.type == 1) {
            this.pk = this.getIntValue() + var;
        } else {
            this.pk = this.getLongValue() + var;
        }
        return this;
    }

    public SplitPk subtract(int var) {
        if (this.type == 1) {
            this.pk = this.getIntValue() - var;
        } else {
            this.pk = this.getLongValue() - var;
        }
        return this;
    }

    @Override
    public String toString() {
        return "SplitPk{" +
                "pk=" + pk +
                ", type=" + type +
                '}';
    }
}
