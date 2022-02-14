package com.gitee.pristine.common.constant;

/**
 * 用户状态
 * @author Pristine Xu
 * @date 2022/2/5 1:29
 * @description:
 */
public class UserStatus {

    // 激活状态
    public final static int ACTIVE = 1;

    // 禁用状态
    public final static int DISABLED = 2;

    /**
     * 用户状态是否为激活
     * @param status
     * @return
     */
    public static boolean isActive(Integer status) {
        if (status == null) {
            return false;
        }
        return status==ACTIVE;
    }

}
