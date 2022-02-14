package com.gitee.pristine.web.config;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.common.result.ErrorResponse;
import com.gitee.pristine.common.result.ErrorStatus;
import com.gitee.pristine.scripts.ex.ScriptCompileException;
import com.gitee.pristine.web.ex.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2021/12/18 15:06
 * @description:
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class ErrorAdviceConfig {

    // 日志
    private final Logger log = LoggerFactory.getLogger(ErrorAdviceConfig.class);

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<ApiResponse> handleServiceException(ServiceException e) {
        // 响应
        return ResponseEntity.ok(ApiResponse.failure(e.getMessage()));
    }

    /**
     * 脚本编译异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ScriptCompileException.class})
    public ResponseEntity<ApiResponse> handleScriptCompileException(ScriptCompileException e) {
        // 响应
        return ResponseEntity.ok(ApiResponse.failure("脚本编译失败，当前脚本可能存在风险"));
    }


    /**
     * 参数错误
     * @HttpStatus 400
     * @param e
     * @return
     */
    @ExceptionHandler(value={
            ValidationException.class,
            BindException.class,
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> handleParametersException(Exception e) {
        // 获取错误消息
        String errorMessage = "非法参数，请求已被拒绝";
        if (e instanceof ValidationException
                || e instanceof MissingServletRequestParameterException
                || e instanceof MissingPathVariableException) {
            errorMessage = e.getMessage();
        }
        else if (e instanceof MethodArgumentNotValidException) {
            errorMessage = ValidationUtil.getParameterErrorMessage((MethodArgumentNotValidException) e, errorMessage);
        }
        else if (e instanceof BindException) {
            errorMessage = ValidationUtil.getParameterErrorMessage((BindException) e, errorMessage);
        }
        else if (e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentTypeMismatchException) {
            errorMessage = "参数类型或格式错误";
        }
        // 响应
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorStatus.ILLEGAL_REQUEST_PARAMETER, errorMessage));
    }



    /**
     * 其它错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleServerException(Exception e) {
        // 这里打印
        e.printStackTrace();
        log.error(e.getMessage());
        // 响应
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorStatus.INTERNAL_SERVER_ERROR));
    }


    /**
     * 参数校验错误信息工具类
     * @author Pristine Xu
     */
    static class ValidationUtil {
        /**
         * 获取参数错误信息
         * @param e
         * @param defaultMessage
         * @return
         */
        private static String getParameterErrorMessage(MethodArgumentNotValidException e, String defaultMessage) {
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            if (!CollectionUtils.isEmpty(fieldErrors)) {
                return fieldErrors.get(0).getDefaultMessage();
            }
            return defaultMessage;
        }

        /**
         * 获取参数错误信息
         * @param e
         * @param defaultMessage
         * @return
         */
        private static String getParameterErrorMessage(BindException e, String defaultMessage) {
            List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
            if (!CollectionUtils.isEmpty(fieldErrors)) {
                return fieldErrors.get(0).getDefaultMessage();
            }
            return defaultMessage;
        }
    }
}
