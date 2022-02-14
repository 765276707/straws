package com.gitee.pristine.domain.base;

import com.gitee.pristine.domain.base.Condition;

/**
 * 单一文本查询条件
 * @author Pristine Xu
 * @date 2022/1/13 2:49
 * @description:
 */
public class TextCondition extends Condition {

    /** 查询文本 */
    private String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isEmpty() {
        return this.searchText == null || "".equalsIgnoreCase(this.searchText);
    }

    /**
     * @return "%searchText%"
     */
    public String like() {
        return "%" + this.searchText + "%";
    }

    /**
     * @return "searchText%"
     */
    public String likeLeft() {
        return this.searchText + "%";
    }

    /**
     * @return "%searchText"
     */
    public String likeRight() {
        return "%" + this.searchText;
    }
}
