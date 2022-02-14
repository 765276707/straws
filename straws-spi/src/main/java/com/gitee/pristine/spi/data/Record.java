package com.gitee.pristine.spi.data;

import com.gitee.pristine.common.utils.BytesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来转换数据的记录模型
 * @author Pristine Xu
 * @date 2022/1/18 1:31
 * @description: 一个Record代表一行记录
 */
public class Record {

    // 列下标
    private String[] columns;

    // 数据
    private Object[] data;

    /**
     * 保存列下标信息
     */
    private Map<String, Integer> colIdxMap;

    private Record() {}

    public Record(String[] columns, Object[] data) {
        if (columns.length !=  data.length) {
            throw new IllegalArgumentException("columns length must equals to data length.");
        }
        this.columns = columns;
        this.data = data;

    }

    private void initColumnMap(String[] columnNames) {
        int length = columnNames.length;
        this.colIdxMap = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            colIdxMap.put(columnNames[i], i);
        }
    }

    /**
     * 获取列的下标
     * @param columnName 列名
     * @return 不存在则会返回 -1
     */
    public int getIndex(String columnName) {
        if (!this.colIdxMap.containsKey(columnName)) {
            return -1;
        }
        return this.colIdxMap.get(columnName);
    }

    /**
     * 获取列的值
     * @param columnName 列名
     * @return
     */
    public Object getValue(String columnName) {
        return this.data[this.getIndex(columnName)];
    }

    /**
     * 获取列的值
     * @param columnIdx 列下标
     * @return
     */
    public Object getValue(int columnIdx) {
        return this.data[columnIdx];
    }

    /**
     * 获取列的数据数组
     * @return Object[]
     */
    public Object[] getData() {
        return data;
    }
}
