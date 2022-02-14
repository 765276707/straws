package com.gitee.pristine.spi.extractor;

import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPk;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.Task;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据抽取器
 * @author Pristine Xu
 * @date 2022/1/13 4:46
 * @description: 负责从目标数据源抽取数据
 */
public interface Extractor {

    /**
     * 设置数据源
     * @param dataSource 数据源
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
    TableMeta getTableMeta();

    /**
     * 查询表数据统计
     * @return
     */
    TableInfo getTableInfo(String tableName, String splitPk);

    /**
     * 查询数据
     * @return
     */
    Result readRecords(SplitPkRange range) throws SQLException;

}
