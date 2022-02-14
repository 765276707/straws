package com.gitee.pristine.aop.encrypt.handler;

import com.gitee.pristine.aop.encrypt.ex.CryptException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pristine Xu
 * @date 2022/2/8 10:53
 * @description:
 */
public interface CryptHandler {

    /** 加解密器类型 */
    String getAlgorithm();

    /** 响应体加密 */
    String encrypt(String plainText, HttpServletRequest request, HttpHeaders headers) throws CryptException;

    /** 请求体解密 */
    String decrypt(String cipherText, HttpServletRequest request, HttpHeaders headers) throws CryptException;

}
