package com.gitee.pristine.core.constant;

/**
 * @author Pristine Xu
 * @date 2022/1/18 14:59
 * @description:
 */
public enum TaskResultStatus {

    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 失败
     */
    FAILURE(2);

    private Integer status;

    TaskResultStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}
