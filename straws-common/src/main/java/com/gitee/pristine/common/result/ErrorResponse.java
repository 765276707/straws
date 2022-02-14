package com.gitee.pristine.common.result;

import java.util.Objects;

/**
 * 错误响应
 * @author Pristine Xu
 * @date 2022/1/13 3:50
 * @description:
 */
public class ErrorResponse {

    private String errorCode;

    private String errorMessage;

    public ErrorResponse(ErrorStatus errorStatus) {
        this.errorCode = errorStatus.getErrorCode();
        this.errorMessage = errorStatus.getErrorMessage();
    }

    public ErrorResponse(ErrorStatus errorStatus, String errorMessage) {
        this.errorCode = errorStatus.getErrorCode();
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return errorCode.equals(that.errorCode) &&
                errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
