package com.gitee.pristine.aop.encrypt.ex;

/**
 * 异常工具类
 * @author Pristine Xu
 * @date 2022/2/8 10:57
 * @description:
 */
public class ExceptionUtil {

    public static CryptException buildCryptEx(String errorMsg, Object ... args) {
        return new CryptException(String.format(errorMsg, args));
    }

}
