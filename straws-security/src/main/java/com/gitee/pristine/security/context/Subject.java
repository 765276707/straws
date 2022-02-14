package com.gitee.pristine.security.context;

/**
 * 当前登录对象
 * @author Pristine Xu
 * @date 2022/2/3 3:57
 * @description:
 */
public class Subject {

    private String username;

    private Subject() {}

    public Subject(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
