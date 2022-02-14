package com.gitee.pristine.common.result;

/**
 * 错误响应码
 * @author xzb
 */
public enum ErrorStatus {

    /** 非法的请求参数 */
    ILLEGAL_REQUEST_PARAMETER(400, "IllegalRequestParameter", "请求参数错误"),

    /** 身份认证失败 */
    UNAUTHORIZED(401, "Unauthorized", "身份认证失败"),

    /** 权限不足 */
    INSUFFICIENT_AUTHORITY(403, "InsufficientAuthority", "访问权限不足"),

    /** 请求方式不被允许 */
    METHOD_NOT_ALLOWED(405,"MethodNotAllowed", "请求方式不被允许"),

    /** 请求中提交的实体并不是服务器中所支持的格式 */
    UNSUPPORTED_MEDIA_TYPE(415,"UnsupportedMediaType", "请求中提交的实体并不是服务器中所支持的格式"),

    /** 请求次数过多 */
    TOO_MANY_REQUESTS(429, "TooManyRequests", "请求次数过多，请稍后再试"),

    /** 服务器内部错误 */
    INTERNAL_SERVER_ERROR(500, "InternalServerError", "服务器内部错误");


    private Integer httpStatus;
    private String errorCode;
    private String errorMessage;

    ErrorStatus(Integer httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
