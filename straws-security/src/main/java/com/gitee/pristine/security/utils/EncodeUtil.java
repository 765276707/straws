package com.gitee.pristine.security.utils;

import com.gitee.pristine.common.utils.HexUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * @author Pristine Xu
 * @date 2022/2/3 22:16
 * @description:
 */
public class EncodeUtil {

    public static String md5(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return HexUtil.bytesToHexStr(md.digest(origin.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
