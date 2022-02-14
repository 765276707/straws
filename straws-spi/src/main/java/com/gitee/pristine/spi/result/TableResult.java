package com.gitee.pristine.spi.result;

/**
 * @author Pristine Xu
 * @date 2022/1/28 5:04
 * @description:
 */
public class TableResult {

    private Integer total;

    private Long maxSplitPk;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getMaxSplitPk() {
        return maxSplitPk;
    }

    public void setMaxSplitPk(Long maxSplitPk) {
        this.maxSplitPk = maxSplitPk;
    }

    @Override
    public String toString() {
        return "TableResult{" +
                "total=" + total +
                ", maxSplitPk=" + maxSplitPk +
                '}';
    }
}
