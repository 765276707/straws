package com.gitee.pristine.common.constant;

/**
 * 同步模式
 * @author Pristine Xu
 * @date 2022/1/13 3:15
 * @description:
 */
public enum Synchronous {

    /**
     * 全量同步，同步之前会清空表数据
     */
    FULL(1),
    /**
     * 增量同步，必须指定splitPk
     */
    INCR(2),
    /**
     * 全表对比同步，比较消耗性能，千万级表慎用
     */
    CDC(3);

    private Integer mode;

    Synchronous(Integer mode) {
        this.mode = mode;
    }

    public Integer getMode() {
        return mode;
    }

    public static Synchronous toMode(Integer modeVal) {
        if (FULL.getMode().equals(modeVal)) {
            return FULL;
        } else if (INCR.getMode().equals(modeVal)) {
            return INCR;
        } else if (CDC.getMode().equals(modeVal)) {
            return CDC;
        } else {
            throw new IllegalArgumentException("Invalid sync mode");
        }
    }

}
