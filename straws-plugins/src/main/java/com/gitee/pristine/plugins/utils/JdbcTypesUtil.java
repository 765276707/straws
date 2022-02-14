package com.gitee.pristine.plugins.utils;

import java.util.Arrays;

/**
 * JDBC类型工具
 * @author Pristine Xu
 * @date 2022/1/25 6:14
 * @description:
 */
public class JdbcTypesUtil {

    /**
     * 匹配jdbc数据类型，兼容到jdbc4.0
     * @param typeId 需要匹配的类型
     * @param typs 被匹配的类型集
     * @return
     */
    public static boolean match(Integer typeId, int ... typs) {
        if (typeId == null) {
            return false;
        }
        return Arrays.stream(typs)
                .anyMatch(typ -> typ == typeId);
    }

}
