package com.gitee.pristine.common.result;

/**
 * 接口默认消息
 * @author Pristine Xu
 * @date 2022/1/13 4:06
 * @description:
 */
public enum  ApiMessage {

    REQUEST_SUCCESS("请求成功"),

    REQUEST_FAILURE("请求失败");

    private String message;

    ApiMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
