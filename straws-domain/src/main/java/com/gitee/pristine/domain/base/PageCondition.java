package com.gitee.pristine.domain.base;

import com.gitee.pristine.domain.base.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询参数
 * @author Pristine Xu
 * @date 2022/1/13 2:49
 * @description:
 */
public class PageCondition extends Condition {

    /** 当前页数 */
    private int pageNum = 1;

    /** 每页大小 */
    private int pageSize = 10;

    /** 排序集合 */
    private List<SortCondition> sorts = new ArrayList<>();

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SortCondition> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortCondition> sorts) {
        this.sorts = sorts;
    }

    /**
     * 排序查询参数
     * @author xzb
     */
    public static class SortCondition {

        // 排序字段
        private String field;

        // 排序类型
        private String order;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }

}
