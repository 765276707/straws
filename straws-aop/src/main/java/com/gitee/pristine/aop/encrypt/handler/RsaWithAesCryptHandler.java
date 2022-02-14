package com.gitee.pristine.aop.encrypt.handler;

import com.gitee.pristine.aop.encrypt.config.CryptProperties;
import com.gitee.pristine.aop.encrypt.constant.Alg;
import com.gitee.pristine.aop.encrypt.ex.CryptException;
import com.gitee.pristine.aop.encrypt.ex.ExceptionUtil;
import com.gitee.pristine.common.utils.AesUtil;
import com.gitee.pristine.common.utils.RsaUtil;
import org.springframework.http.HttpHeaders;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;

/**
 * 混合加密算法
 * @author Pristine Xu
 * @date 2022/2/8 10:55
 * @description: 虽说效率不是很高，但是数据安全性较高，所以选择这个
 */
public class RsaWithAesCryptHandler implements CryptHandler {

    @Resource
    private CryptProperties cryptProperties;

    @Override
    public String getAlgorithm() {
        return Alg.RSA_AES;
    }

    @Override
    public String encrypt(String plainText, HttpServletRequest request, HttpHeaders headers) throws CryptException {
        // 随机生成一个AES的密钥
        String aesKey = this.randomGeneratorAesKey();
        try {
            // RSA加密密钥
            String cAesKey = RsaUtil.privateEncrypt(plainText, this.getPrivateKey());
            // 加入响应头
            headers.add(this.getAesKeyInHeader(), cAesKey);
            // AES加密响应数据
            return AesUtil.encrypt(plainText, aesKey, cryptProperties.getAesIv());
        }  catch (Exception e) {
            throw ExceptionUtil.buildCryptEx(
                    "an error has occurred when plain text encrypted with aes algorithm cause by : %s", e.getMessage());
        }
    }

    @Override
    public String decrypt(String cipherText, HttpServletRequest request, HttpHeaders headers) throws CryptException {
        // 获取AES的密钥
        String cAesKey = this.getAesKeyFromHeader(headers);
        try {
            // RSA解密密钥
            String aesKey = RsaUtil.privateDecrypt(cAesKey, this.getPrivateKey());
            // AES解密请求体内容
            return AesUtil.decrypt(cipherText, aesKey, cryptProperties.getAesIv());
        }  catch (Exception e) {
            throw ExceptionUtil.buildCryptEx(
                    "an error has occurred when cipher text decrypted with aes algorithm cause by : %s", e.getMessage());
        }
    }

    /**
     * 获取Aes密钥
     * @param headers 请求头
     * @return
     */
    private String getAesKeyFromHeader(HttpHeaders headers) {
        List<String> aesKeys = headers.get(this.getAesKeyInHeader());
        if (aesKeys==null || aesKeys.isEmpty()) {
            throw new IllegalArgumentException("缺失必要的请求头");
        }
        return aesKeys.get(0);
    }

    /**
     * 生成随机16位的AES密钥
     * @return
     */
    private String randomGeneratorAesKey() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 15)
                .toUpperCase();
    }

    /**
     * 获取令牌公钥
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(cryptProperties.getRsaPublicKey());
    }

    /**
     * 获取令牌私钥
     * @return
     */
    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PrivateKey(cryptProperties.getRsaPrivateKey());
    }

    /**
     * 获取在请求头的中AES密钥
     * @return
     */
    private String getAesKeyInHeader() {
        return cryptProperties.getAesHeaderKey();
    }
}
