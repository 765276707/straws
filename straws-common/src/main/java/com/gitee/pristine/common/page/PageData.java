package com.gitee.pristine.common.page;

import java.util.Collection;

/**
 * @author Pristine Xu
 * @date 2022/1/13 3:27
 * @description:
 */
public class PageData<T> {

    /** 当前页数 */
    private int pageNum;

    /** 每页大小 */
    private int pageSize;

    /** 总条目数 */
    private long total;

    /** 结果集  */
    private Collection<T> list;

    private PageData() {
    }

    public static <T> PageData<T> builder() {
        return new PageData<>();
    }

    public PageData pageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageData pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageData total(long total) {
        this.total = total;
        return this;
    }

    public PageData list(Collection<T> list) {
        this.list = list;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public Collection<T> getList() {
        return list;
    }

}
