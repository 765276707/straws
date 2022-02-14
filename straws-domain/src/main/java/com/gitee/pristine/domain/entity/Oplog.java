package com.gitee.pristine.domain.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_oplog")
public class Oplog {
    @Id
    private Long id;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 类型对应的颜色，拓展方便前端组件定义组件颜色
     */
    @Column(name = "type_color")
    private String typeColor;

    /**
     * 操作内容或描述
     */
    private String description;

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

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作类型
     *
     * @return type - 操作类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置操作类型
     *
     * @param type 操作类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取类型对应的颜色，拓展方便前端组件定义组件颜色
     *
     * @return type_color - 类型对应的颜色，拓展方便前端组件定义组件颜色
     */
    public String getTypeColor() {
        return typeColor;
    }

    /**
     * 设置类型对应的颜色，拓展方便前端组件定义组件颜色
     *
     * @param typeColor 类型对应的颜色，拓展方便前端组件定义组件颜色
     */
    public void setTypeColor(String typeColor) {
        this.typeColor = typeColor == null ? null : typeColor.trim();
    }

    /**
     * 获取操作内容或描述
     *
     * @return description - 操作内容或描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置操作内容或描述
     *
     * @param description 操作内容或描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取操作的方法名
     *
     * @return method - 操作的方法名
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置操作的方法名
     *
     * @param method 操作的方法名
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 获取操作时间
     *
     * @return optime - 操作时间
     */
    public Date getOptime() {
        return optime;
    }

    /**
     * 设置操作时间
     *
     * @param optime 操作时间
     */
    public void setOptime(Date optime) {
        this.optime = optime;
    }

    /**
     * 获取操作用户，这里存储的是用户名 username
     *
     * @return opuser - 操作用户，这里存储的是用户名 username
     */
    public String getOpuser() {
        return opuser;
    }

    /**
     * 设置操作用户，这里存储的是用户名 username
     *
     * @param opuser 操作用户，这里存储的是用户名 username
     */
    public void setOpuser(String opuser) {
        this.opuser = opuser == null ? null : opuser.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}