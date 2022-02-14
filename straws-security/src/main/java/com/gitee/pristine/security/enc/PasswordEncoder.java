package com.gitee.pristine.security.enc;

import com.gitee.pristine.security.utils.EncodeUtil;
import org.springframework.stereotype.Component;


/**
 * 密码加密器
 * @author Pristine Xu
 * @date 2022/2/3 22:07
 * @description: 对敏感的密码进行加密保存 和 校验
 */
@Component
public class PasswordEncoder {

    // 固定前缀和后缀
    private final static String PREFIX = "$";
    private final static String SUFFIX = "!";

    /**
     * 对密码进行加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public String encode(String password, String salt) {
        if (password==null || password.equals("")) {
            throw new IllegalArgumentException("password will be encoded can not be null");
        }
        if (salt==null || salt.equals("")) {
            throw new IllegalArgumentException("salt will be encoded can not be null");
        }
        String var = PREFIX + password + salt + SUFFIX;
        return EncodeUtil.md5(var);
    }

    /**
     * 匹配两个密码是否一致
     * @param oldPwdEncoded 已加密完的待比较密码
     * @param newPwd 未加密的要比较的密码
     * @param salt 盐
     * @return
     */
    public boolean match(String oldPwdEncoded, String newPwd, String salt) {
        if (oldPwdEncoded==null || oldPwdEncoded.equals("")) {
            throw new IllegalArgumentException("old password will be compared can not be null");
        }
        String newPwdEncoded = this.encode(newPwd, salt);
        return oldPwdEncoded.equals(newPwdEncoded);
    }

}
