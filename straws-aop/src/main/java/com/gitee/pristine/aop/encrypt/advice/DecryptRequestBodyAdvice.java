package com.gitee.pristine.aop.encrypt.advice;


import com.gitee.pristine.aop.encrypt.annotation.ApiCryptBody;
import com.gitee.pristine.aop.encrypt.annotation.ApiDecryptBody;
import com.gitee.pristine.aop.encrypt.core.CryptManager;
import com.gitee.pristine.aop.encrypt.ex.ExceptionUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 对请求体内容进行增强
 * @author xzbcode
 */
@ControllerAdvice
@SuppressWarnings("all")
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CryptManager cryptManager;


    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> clazz) {
        // 当且仅当有 DecryptReqBody 注解时才解密请求体
        Method method = methodParameter.getMethod();
        if (method != null) {
            return AnnotationUtils.findAnnotation(method, ApiDecryptBody.class) != null
                    ||  AnnotationUtils.findAnnotation(method, ApiCryptBody.class) != null;
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                           Class<? extends HttpMessageConverter<?>> clazz) throws IOException {
        // 判断方法是否有解密注解
        return new DecryptHttpInputMessage(httpInputMessage, methodParameter);
    }

    @Override
    public Object afterBodyRead(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                Class<? extends HttpMessageConverter<?>> clazz) {
        // TODO Auto-generated method stub
        return object;
    }

    @Override
    public Object handleEmptyBody(Object object, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type,
                                  Class<? extends HttpMessageConverter<?>> clazz) {
        // TODO Auto-generated method stub
        return object;
    }

    /**
     * 实现并重写HttpInputMessage
     * @author xzb
     */
    class DecryptHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;
        MethodParameter method;

        DecryptHttpInputMessage(HttpInputMessage httpInputMessage, MethodParameter method) throws IOException {
            this.headers = httpInputMessage.getHeaders();
            this.body = httpInputMessage.getBody();
            this.method = method;
        }

        @Override
        public InputStream getBody() throws IOException {
            // 输入流不为空的时候
            if (null != body) {
                // 替换非url安全字符（jdk8之后replaceAll性能优于replace）
                String strBody = IOUtils.toString(body)
                        .replaceAll("%2B", "+")
                        .replaceAll("%3D", "=");

                // 解析参数
                String decryptStrBody = "";
                try {
                    // 开启调试
                    if (log.isDebugEnabled()) {
                        log.debug("parameters in request body before decrypted： {}", body);
                    }

                    decryptStrBody = cryptManager.decrypt(strBody, null, headers);

                    if (log.isDebugEnabled()) {
                        log.debug("parameters in request body after decrypted： {}", decryptStrBody);
                    }
                }
                catch (Exception e)
                {
                    if (log.isErrorEnabled()) {
                        // 错误日志
                        log.error("parameters in request body be decrypted failure, casue by {}", e.getMessage());
                    }
                    throw ExceptionUtil.buildCryptEx(
                            "parameters in request body be decrypted failure. cause by {}", e.getMessage(), e.getCause());
                }

                // 返回解密后的数据流
                return IOUtils.toInputStream(decryptStrBody, "UTF-8");
            }
            // 返回原始数据
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
