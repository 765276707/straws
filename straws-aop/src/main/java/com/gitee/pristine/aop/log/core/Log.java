package com.gitee.pristine.aop.log.core;

import java.util.Date;

/**
 * @author Pristine Xu
 * @date 2022/2/7 17:48
 * @description:
 */
public class Log {

    /**
     * 操作类型
     */
    private String type;

    /**
     * 类型对应的颜色
     */
    private String color;

    /**
     * 操作内容或描述
     */
    private String desc;

    /**
     * 操作的方法名
     */
    private String method;

    /**
     * 操作时间
     */
    private Date optime;

    /**
     * 操作用户，这里存储的是用户名 username
     */
    private String opuser;

    public Log(String type, String color, String desc, String method, Date optime, String opuser) {
        this.type = type;
        this.color = color;
        this.desc = desc;
        this.method = method;
        this.optime = optime;
        this.opuser = opuser;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getMethod() {
        return method;
    }

    public Date getOptime() {
        return optime;
    }

    public String getOpuser() {
        return opuser;
    }

    public String getColor() {
        return color;
    }
}
