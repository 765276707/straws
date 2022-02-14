package com.gitee.pristine.security.enc;

import java.util.UUID;

/**
 * 盐生成器
 * @author Pristine Xu
 * @date 2022/2/4 2:19
 * @description:
 */
public class SaltGenerator {

    /**
     * 生成随机盐
     * @param length 盐的长度
     * @return
     */
    public static String generate(int length) {
        if (length >= 10) {
            throw new IllegalArgumentException("salt length must less than 10");
        }
        return UUID.randomUUID().toString()
                .replace("-","").trim().substring(0, length);
    }

}
