package com.gitee.pristine.domain.dto;

import com.gitee.pristine.domain.base.BaseObject;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 测试链接数据传输对象
 * @author Pristine Xu
 * @date 2022/1/19 5:34
 * @description: 用来测试连接是否可用
 */
public class CheckConnDTO extends BaseObject {

    @NotEmpty(message = "数据源驱动不能为空")
    private String driverClassName;

    @NotEmpty(message = "JDBC URL不能为空")
    private String jdbcUrl;

    @NotEmpty(message = "数据源用户名不能为空")
    private String username;

    @NotEmpty(message = "数据源密码不能为空")
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
