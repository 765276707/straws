package com.gitee.pristine.plugins.mysql;

import com.gitee.pristine.plugins.common.CommonJdbcLoader;

import java.util.List;

/**
 * MySQL Loader
 * @author Pristine Xu
 * @date 2022/1/14 5:24
 * @description: 兼容 JDBC Loader
 */
public class MysqlLoader extends CommonJdbcLoader {

    @Override
    public void writeRecords(List<Object[]> records) {
        this.jdbcTemplate.batchUpdate(insertSql, records);
    }

}
