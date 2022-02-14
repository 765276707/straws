package com.gitee.pristine.datasource.info;

import com.gitee.pristine.datasource.split.SplitPk;

/**
 * 自增主键
 * @author Pristine Xu
 * @date 2022/1/18 1:56
 * @description:
 */
public class TableInfo {

    // 记录总数
    private int total;
    // 起始查询位置
    private SplitPk start;
    // 结束查询位置
    private SplitPk finish;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public SplitPk getStart() {
        return start;
    }

    public void setStart(SplitPk start) {
        this.start = start;
    }

    public SplitPk getFinish() {
        return finish;
    }

    public void setFinish(SplitPk finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "total=" + total +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}
