package com.gitee.pristine.common.result;

import java.util.Objects;

/**
 * 接口响应
 * @author Pristine Xu
 * @date 2022/1/13 3:50
 * @description:
 */
public class ApiResponse<T> {

    private Boolean success;

    private String message;

    private T data;

    private ApiResponse(Boolean success) {
        this.success = success;
    }

    private ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private ApiResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true);
    }

    public static <T> ApiResponse<T> failure() {
        return new ApiResponse<>(false);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<T>(false, message);
    }

    public ApiResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> message(ApiMessage message) {
        this.message = message.getMessage();
        return this;
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse<?> that = (ApiResponse<?>) o;
        return Objects.equals(success, that.success) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
