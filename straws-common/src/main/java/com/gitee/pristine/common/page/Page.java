package com.gitee.pristine.common.page;

import com.gitee.pristine.common.utils.ReflectUtil;
import com.github.pagehelper.PageInfo;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 分页助理
 * @author Pristine Xu
 * @date 2022/1/13 3:27
 * @description:
 */
public class Page {

    /**
     * 将同数据类型的 PageInfo 转换成 PageData
     * @param pi
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> PageData toData(PageInfo<T> pi) {
        return PageData.builder()
                .pageNum(pi.getPageNum())
                .pageSize(pi.getPageSize())
                .total(pi.getTotal())
                .list(pi.getList());
    }

    /**
     * 将不同数据类型的 PageInfo 转换成 PageData
     * @param pi
     * @param targetClass 转换的目标数据类型
     * @param <R>
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R, T> PageData toData(PageInfo<R> pi, Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("page targetClass can not be null.");
        }
        // 集合类型转换
        Collection<T> collectList = pi.getList()
                .stream()
                .map(source -> ReflectUtil.convertClassType(source, targetClass))
                .collect(Collectors.toList());
        return PageData.builder()
                .pageNum(pi.getPageNum())
                .pageSize(pi.getPageSize())
                .total(pi.getTotal())
                .list(collectList);
    }

}
