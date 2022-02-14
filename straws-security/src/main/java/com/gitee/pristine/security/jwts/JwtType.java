package com.gitee.pristine.security.jwts;

/**
 * Token 类型
 * @author Pristine Xu
 * @date 2022/2/3 2:43
 * @description:
 */
public enum JwtType {

    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh");

    private String value;

    JwtType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
