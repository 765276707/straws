package com.gitee.pristine.spi.loader;

import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.Task;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据装载器
 * @author Pristine Xu
 * @date 2022/1/13 4:47
 * @description: 负责将数据装在到目的数据源
 */
public interface Loader {

    /**
     * 设置数据源
     * @param dataSource
     */
    void setDataSource(DataSource dataSource);

    /**
     * 设置数据库字典
     * @param databaseDialect
     */
    void setDatabaseDialect(DatabaseDialect databaseDialect);

    /**
     * 设置相关参数，用来构建sql
     * @param task
     */
    void setTaskDetail(Task task);

    /**
     * 查询表结构
     * @return
     */
    TableMeta getTableMeta(String tableName);

    /**
     * 从来源库查询数据
     * @param range
     * @return
     */
    Result readRecords(SplitPkRange range) throws SQLException;

    /**
     * 写入数据到目标库
     * @param records
     */
    void writeRecords(List<Object[]> records);

    /**
     * 更新数据到目标库
     * @param records
     */
    void updateRecords(TableMeta originTableMeta, List<Object[]> records);

    /**
     * 删除目标库的数据
     * @param records
     */
    void deleteRecords(TableMeta originTableMeta, List<Object[]> records);

    /**
     * 清空表数据
     * @param sql
     */
    void clearRecords(String sql);

    /**
     * 清空指定区间的数据
     * @param tableName
     * @param tableInfo
     */
    public void deleteRecords(String tableName, String splitPk, TableInfo tableInfo);
}
