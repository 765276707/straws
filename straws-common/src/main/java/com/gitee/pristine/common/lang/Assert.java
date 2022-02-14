package com.gitee.pristine.common.lang;

/**
 * 断言
 * @author Pristine Xu
 * @date 2022/1/13 5:00
 * @description:
 */
public class Assert {

    /**
     * 参数不为空
     * @param var 参数
     * @param errorMsg 错误消息
     * @throws IllegalArgumentException 不合法参数异常
     */
    public static void notBlank(String var, String errorMsg) {
        if (var==null || "".equals(var)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 参数不为Null
     * @param var
     * @param errorMsg
     */
    public static void notNull(Object var, String errorMsg) {
        if (var == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

}
