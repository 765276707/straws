package com.gitee.pristine.aop.encrypt.advice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.pristine.aop.encrypt.annotation.ApiCryptBody;
import com.gitee.pristine.aop.encrypt.annotation.ApiEncryptBody;
import com.gitee.pristine.aop.encrypt.core.CryptManager;
import com.gitee.pristine.aop.encrypt.ex.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 对响应内容进行加密
 * @author xzbcode
 */
@ControllerAdvice
@SuppressWarnings("all")
public class EncryptResponseBodyAdvice<T> implements ResponseBodyAdvice<T> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CryptManager cryptManager;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 当且仅当有 EncryptRspBody 注解时才加密响应体
        Method method = methodParameter.getMethod();
        if (method != null) {
            return AnnotationUtils.findAnnotation(method, ApiEncryptBody.class) != null
                    ||  AnnotationUtils.findAnnotation(method, ApiCryptBody.class) != null;
        }
        return false;
    }

    @Override
    public T beforeBodyWrite(T data, MethodParameter method, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // 调用转换器，返回加密后的数据
        try
        {
            // 反序列化操作
            String strNeedEncData = this.convertJson(data);
            // 加密
            String encryptedData = this.cryptManager.encrypt(strNeedEncData, null, request.getHeaders());
            // 开启调试
            if (log.isDebugEnabled()) {
                log.debug("response body before encrypted：{}，response body after encrypted：{}", strNeedEncData, encryptedData);
            }
            // 加密后操作
            return (T) encryptedData;
        }
        catch (Exception e)
        {
            log.error("response data encrypt fail, cause by : {}", e.getMessage());
            throw ExceptionUtil.buildCryptEx(e.getMessage(), e.getCause());
        }
    }

    protected String convertJson(Object needEncData) throws Exception {
        return objectMapper.writeValueAsString(needEncData);
    }
}
