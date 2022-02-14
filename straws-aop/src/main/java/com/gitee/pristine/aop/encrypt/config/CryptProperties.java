package com.gitee.pristine.aop.encrypt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Pristine Xu
 * @date 2022/2/8 11:01
 * @description:
 */
@ConfigurationProperties(prefix = "straws.crypt")
public class CryptProperties {

    // AES加密后的内容的密钥
    private String aesHeaderKey = "Data-AESKey";
    // AES偏移量
    private String aesIv = "";
    // RSA私钥
    private String rsaPrivateKey = "";

    // RSA公钥
    private String rsaPublicKey = "";

    public String getAesHeaderKey() {
        return aesHeaderKey;
    }

    public void setAesHeaderKey(String aesHeaderKey) {
        this.aesHeaderKey = aesHeaderKey;
    }

    public String getAesIv() {
        return aesIv;
    }

    public void setAesIv(String aesIv) {
        this.aesIv = aesIv;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }
}
