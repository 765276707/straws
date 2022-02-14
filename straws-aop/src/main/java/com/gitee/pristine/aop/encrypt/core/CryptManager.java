package com.gitee.pristine.aop.encrypt.core;

import com.gitee.pristine.aop.encrypt.ex.CryptException;
import com.gitee.pristine.aop.encrypt.handler.CryptHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author Pristine Xu
 * @date 2022/2/8 11:09
 * @description:
 */
public class CryptManager {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CryptHandler cryptHandler;


    /**
     * 加密
     * @param headers 请求头
     * @param plainText 明文
     * @return String
     * @throws CryptException 加密失败异常
     */
    public String encrypt(String plainText, HttpServletRequest request, HttpHeaders headers) throws CryptException {
        return this.cryptHandler.encrypt(plainText, request, headers);
    }

    /**
     * 解密
     * @param headers 请求头
     * @param cipherText 密文
     * @return String
     * @throws CryptException 解密失败异常
     */
    public String decrypt(String cipherText, HttpServletRequest request, HttpHeaders headers) throws CryptException {
        return this.cryptHandler.decrypt(cipherText, request, headers);
    }

}
