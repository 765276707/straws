package com.gitee.pristine.core.chunk.conf;

import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/19 2:26
 * @description:
 */
public class CdcEntity {

    private List<Object[]> insertList;
    private List<Object[]> updateList;
    private List<Object[]> deleteList;

    public CdcEntity(List<Object[]> insertList, List<Object[]> updateList, List<Object[]> deleteList) {
        this.insertList = insertList;
        this.updateList = updateList;
        this.deleteList = deleteList;
    }

    public List<Object[]> getInsertList() {
        return insertList;
    }

    public List<Object[]> getUpdateList() {
        return updateList;
    }

    public List<Object[]> getDeleteList() {
        return deleteList;
    }
}
