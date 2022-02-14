package com.gitee.pristine.spi.transformer;

import com.gitee.pristine.spi.data.Record;

/**
 * 数据转换器
 * @author Pristine Xu
 * @date 2022/1/13 4:48
 * @description: 负责对数据进行转换、清洗
 */
public interface Transformer {

    /**
     * 数据转换、清洗
     * @param record 旧的数据
     * @return Object[] 新的数据
     */
    Object[] transfer(Object[] record);

}
