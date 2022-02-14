package com.gitee.pristine.plugins.mssql;

import com.gitee.pristine.plugins.common.CommonJdbcLoader;

import java.util.List;

/**
 * Sql Server Loader
 * @author Pristine Xu
 * @date 2022/1/14 5:25
 * @description: 兼容 JDBC Loader
 */
public class MssqlLoader extends CommonJdbcLoader {

    /**
     * 开启SQL SERVER对自增列可插入的支持
     * @param records
     */
    @Override
    public void writeRecords(List<Object[]> records) {
        try
        {
            this.jdbcTemplate.update("SET IDENTITY_INSERT "+task.getTargetTableName()+" ON");
            this.jdbcTemplate.batchUpdate(insertSql, records);
        }
        finally
        {
            this.jdbcTemplate.update("SET IDENTITY_INSERT "+task.getTargetTableName()+" OFF");
        }
    }

}
