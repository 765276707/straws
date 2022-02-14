package com.gitee.pristine.datasource.type;

import com.gitee.pristine.datasource.meta.ColumnMeta;

/**
 * @author Pristine Xu
 * @date 2022/2/2 8:25
 * @description:
 */
public interface ColumnTypeConvert {

    public String convert(ColumnMeta column);

}
