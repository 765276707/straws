package com.gitee.pristine.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 * @author Pristine Xu
 * @date 2021/12/28 16:01
 * @description:
 */
public class CollectionUtil {

    /**
     * 根据切片容量对集合进行切片
     * @param collection 集合
     * @param capacity 切片容量
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> collection, int capacity) {
        if (collection==null || capacity==0) {
            return new ArrayList<>();
        }
        List<List<T>> results = new ArrayList<>();
        int totalSize = collection.size();
        // 容量小于指定数据容量
        if (totalSize <= capacity) {
            results.add(collection);
        } else {
            // 计算需要切分成多少份
            int sliceNum = totalSize / capacity;
            int lastCount = totalSize % capacity;
            // 取商
            for (int i = 0; i < sliceNum; i++) {
                List<T> var1 = collection.subList(i*capacity, (i+1)*capacity);
                results.add(var1);
            }
            // 追加取余
            if (lastCount > 0) {
                int startIndex = sliceNum*capacity;
                List<T> var2 = collection.subList(startIndex, startIndex + lastCount);
                results.add(var2);
            }
        }
        return results;
    }

    /**
     * 获取集合中指定区间的子集合
     * @param collection 集合
     * @param startIndex 开始下标
     * @param finishIndex 结束小标
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> collection, int startIndex, int finishIndex) {
        if (finishIndex < startIndex) {
            throw new IllegalArgumentException("finishIndex must large than or equals startIndex.");
        }
        int size = finishIndex - startIndex;
        if (size == 0) {
            size = 1;
        }
        final List<T> res = new ArrayList<>(size);
        for (int i = 0; i < collection.size(); i++) {
            if (i>=startIndex && i<finishIndex) {
                res.add(collection.get(i));
            }
        }
        return res;
    }


}
