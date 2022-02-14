package com.gitee.pristine.datasource.split;

/**
 * 切割主键范围
 * @author Pristine Xu
 * @date 2022/1/13 5:36
 * @description: 一个Range对应一个Chunk
 */
public class SplitPkRange {

    private SplitPk startValue;

    private SplitPk finishValue;

    private SplitPkRange() {}

    public SplitPkRange(SplitPk startValue, SplitPk finishValue) {
        this.startValue = startValue;
        this.finishValue = finishValue;
    }

    public static SplitPkRange builder() {
        return new SplitPkRange();
    }

    public SplitPkRange startWith(SplitPk startValue) {
        this.startValue = startValue;
        return this;
    }

    public SplitPkRange finishWith(SplitPk finishValue) {
        this.finishValue = finishValue;
        return this;
    }

    public SplitPk getStartValue() {
        return startValue;
    }

    public SplitPk getFinishValue() {
        return finishValue;
    }

    @Override
    public String toString() {
        return "SplitPkRange{" +
                "startValue=" + startValue +
                ", finishValue=" + finishValue +
                '}';
    }
}
