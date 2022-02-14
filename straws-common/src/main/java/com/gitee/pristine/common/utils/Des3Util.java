package com.gitee.pristine.common.utils;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class Des3Util {

    /**
     * 3DES的CBC模式加密
     * @param key 密钥
     * @param iv 偏移量
     * @param data 数据
     * @return 加密后的字符串
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public static String encryptDes3Encode(String key, byte[] iv, String data)
            throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Key deskey = null;
        // 添加pkcs7支持
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 转key，java机制必须截取前面24位
        DESedeKeySpec spec = new DESedeKeySpec(HexUtil.hexStr2Bytes(key));
        // 获取安全工厂,获取加密算法
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede","BC");
        deskey = keyFactory.generateSecret(spec);
        // 设置加密策略
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        // 设置偏移量
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        // 初始化加密属性
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ivParameterSpec);
        // 执行加密并且返回
        return new String(Base64.encode(cipher.doFinal(data.getBytes("utf-8"))));
    }


    /**
     * 3DES的CBC模式解密
     * @param key 密钥
     * @param iv 偏移量
     * @param data 数据
     * @return 解密后的字符串
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String decryptDes3Encode(String key, byte[] iv, String data)
            throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Key deskey = null;
        // 添加pkcs7支持
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 转key，java机制必须截取前面24位
        DESedeKeySpec spec = new DESedeKeySpec(HexUtil.hexStr2Bytes(key));
        // 获取安全工厂,获取解密算法
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede","BC");
        deskey = keyFactory.generateSecret(spec);
        // 设置解密策略
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        // 设置偏移量
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        // 初始化解密属性
        cipher.init(Cipher.DECRYPT_MODE, deskey, ivParameterSpec);
        // 执行解密并返回
        return new String(cipher.doFinal(Base64.decode(data)),"utf-8");
    }


}
