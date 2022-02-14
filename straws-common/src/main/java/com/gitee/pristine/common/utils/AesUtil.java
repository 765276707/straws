package com.gitee.pristine.common.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AesUtil {

    // 算法
    private final static String AES = "AES";
    // CBC的填充方式
    private final static String AES_CBC_PADDING = "AES/CBC/PKCS5Padding";
    // 编码类型
    private final static String AES_ENCODED = "UTF-8";


    /**
     * 加密（cbc模式）
     * @param data 数据
     * @param secret 密钥
     * @param iv 偏移量
     * @return String
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String data, String secret, String iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        // 为了能与 ios 统一，这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(AES_ENCODED));
        // 同样对加密后数据进行 base64 编码
        return Base64.encodeBase64String(encryptedBytes);
    }


    /**
     * url安全编码加密（cbc模式）
     * @param data 数据
     * @param secret 密钥
     * @param iv 偏移量
     * @return String
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encryptUrlSafe(String data, String secret, String iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        // 为了能与 ios 统一，这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(AES_ENCODED));
        // 同样对加密后数据进行 base64 编码
        return Base64.encodeBase64URLSafeString(encryptedBytes);
    }


    /**
     * 解密（cbc模式）
     * @param data 数据
     * @param secret 密钥
     * @param iv 偏移量
     * @return String
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(String data, String secret, String iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
                    InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
                    UnsupportedEncodingException {
        // 生成秘钥
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), AES);
        // 转换并设置偏移量
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 设置模式
        Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        // 返回结果
        return new String(cipher.doFinal(Base64.decodeBase64(data)), AES_ENCODED);
    }
}
