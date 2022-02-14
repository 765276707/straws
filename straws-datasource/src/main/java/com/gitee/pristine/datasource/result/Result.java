package com.gitee.pristine.datasource.result;

import com.gitee.pristine.common.lang.Assert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 结果集包装类
 * @author Pristine Xu
 * @date 2022/1/14 4:43
 * @description: 对查询结果集进行二次包装
 */
public class Result {

    private ResultSet resultSet;
    private Statement statement;
    private Connection connection;

    private Result() {}

    public Result(ResultSet resultSet, Statement statement, Connection connection) {
        Assert.notNull(resultSet, "ResultSet can not be null.");
        Assert.notNull(statement, "Statement can not be null.");
        Assert.notNull(connection, "Connection can not be null.");
        this.resultSet = resultSet;
        this.statement = statement;
        this.connection = connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * 关闭连接资源
     */
    public void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
