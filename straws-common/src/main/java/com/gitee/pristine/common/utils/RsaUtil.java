package com.gitee.pristine.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {

    // 算法
    private final static String RSA = "RSA";
    // 秘钥长度
    private final static int KEY_SIZE = 1024;
    // 算法填充模式
    private final static String RSA_ECB_PADDING = "RSA/ECB/PKCS1Padding";
    // 编码类型
    private final static String RSA_ENCODED = "UTF-8";


    /**
     * 生成RSA秘钥
     * @throws NoSuchAlgorithmException 没有该算法
     * @return 秘钥对
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        // 加密长度
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }


    /**
     * 获取RSA公钥并转为Base64编码
     *
     * @param keyPair 秘钥对
     * @return RSA公钥
     */
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        return Base64.encodeBase64String(publicKey.getEncoded());
    }


    /**
     * 获取RSA私钥并转为Base64编码
     *
     * @param keyPair 秘钥对
     * @return RSA私钥
     */
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64.encodeBase64String(privateKey.getEncoded());
    }


    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     *
     * @param pubStr Base64编码后的公钥
     * @return PublicKey公钥对象
     * @throws Exception
     */
    public static PublicKey string2PublicKey(String pubStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.decodeBase64(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }


    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     *
     * @param priStr Base64编码后的私钥
     * @return PrivateKey私钥对象
     */
    public static PrivateKey string2PrivateKey(String priStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.decodeBase64(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * 公钥加密 在移动端获取解密的Cipher类时要使用Cipher.getInstance("RSA/ECB/PKCS1Padding");
     * 在后端使用Cipher.getInstance("RSA")来获取
     * @param content 内容
     * @param publicKey 公钥
     * @return 加密后的字符串
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String publicEncrypt(String content, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                    UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes(RSA_ENCODED)));
    }


    /**
     * 私钥去解密 在移动端获取解密的Cipher类时要使用Cipher.getInstance("RSA/ECB/PKCS1Padding");
     * 在后端使用Cipher.getInstance("RSA");来获取
     * @param content 密文
     * @param privateKey 私钥
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String privateDecrypt(String content, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                    BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(content)), RSA_ENCODED);
    }


    /**
     * 私钥加密
     * @param content 内容
     * @param privateKey 私钥
     * @return 加密后的字符串
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String privateEncrypt(String content, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                    UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes(RSA_ENCODED)));
    }


    /**
     * 公钥解密
     * @param content 密文
     * @param publicKey 公钥
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    public static String publicDecrypt(String content, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
                    BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(RSA_ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.decodeBase64(content)), RSA_ENCODED);
    }
}
