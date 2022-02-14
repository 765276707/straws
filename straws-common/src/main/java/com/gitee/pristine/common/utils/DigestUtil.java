package com.gitee.pristine.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 * @author xzb
 */
public class DigestUtil {

    private final static String CHARSET = "UTF-8";

    /**
     * 速度：MD5 > SHA-1 > SHA-256
     * 安全性：MD5 < SHA-1 << SHA-256
     * 哈希值长度：MD5(128bit) < SHA-1(160bit) < SHA-256(256bit)
     */

    /**
     * md5 签名
     * @param origin 签名内容
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5(String origin)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(CHARSET)));//正确的写法
    }


    /**
     * sha1 签名
     * @param origin 签名内容
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1(String origin)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(CHARSET)));//正确的写法
    }


    /**
     * sha256 签名
     * @param origin 签名内容
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256(String origin)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA256");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(CHARSET)));//正确的写法
    }
}
