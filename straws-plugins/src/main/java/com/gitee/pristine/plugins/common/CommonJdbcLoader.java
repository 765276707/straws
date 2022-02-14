package com.gitee.pristine.plugins.common;

import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.processor.DatabaseMetaAnalysisProcessor;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.plugins.utils.CommonSqlUtil;
import com.gitee.pristine.spi.loader.Loader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 通用的JDBC Loader
 * @author Pristine Xu
 * @date 2022/1/19 3:11
 * @description:
 */
public abstract class CommonJdbcLoader implements Loader {

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;
    protected DatabaseMetaAnalysisProcessor databaseMetaAnalysisProcessor;
    protected DatabaseDialect dialect;
    protected Task task;
    protected String insertSql;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.databaseMetaAnalysisProcessor = new DatabaseMetaAnalysisProcessor(dataSource);
    }

    @Override
    public void setDatabaseDialect(DatabaseDialect databaseDialect) {
        this.dialect = databaseDialect;
    }

    @Override
    public void setTaskDetail(Task task) {
        this.task = task;
        this.insertSql = dialect.generateInsertSql(task.getTargetTableName(), task.getColumns());
    }

    @Override
    public TableMeta getTableMeta(String tableName) {
        return this.databaseMetaAnalysisProcessor
                .analysisTableMeta(tableName);
    }

    @Override
    public Result readRecords(SplitPkRange range) throws SQLException {
        String selectSql = dialect.generateQuerySql(task.getOriginTableName(), task.getColumns(), task.getSplitPk(), range);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.setFetchSize(1000);
        statement.executeQuery(selectSql);
        ResultSet resultSet = statement.executeQuery(selectSql);
        return new Result(resultSet, statement, connection);
    }

//    @Override
//    public void writeRecords(List<Object[]> records) {
//        this.jdbcTemplate.batchUpdate(insertSql, records);
//    }

    @Override
    public void updateRecords(TableMeta originTableMeta, List<Object[]> records) {
        // 构建更新sql
        String sql = this.dialect.generateUpdateSql(this.task.getTargetTableName(), this.task.getColumns(), this.task.getSplitPk());
        // 调整更新参数
        List<Object[]> recordsAfterAdjust = CommonSqlUtil.adjustUpdateParams(originTableMeta, this.task.getColumns(), records);
        this.jdbcTemplate.batchUpdate(sql, recordsAfterAdjust);
    }

    @Override
    public void deleteRecords(TableMeta originTableMeta, List<Object[]> records) {
        // 构建删除sql
        String sql = this.dialect.generateDeleteSql(this.task.getTargetTableName(), this.task.getSplitPk());
        // 调整删除参数
        List<Object[]> recordsAfterAdjust = CommonSqlUtil.adjustDeleteParams(originTableMeta, this.task.getColumns(), records);
        this.jdbcTemplate.batchUpdate(sql, recordsAfterAdjust);
    }

    @Override
    public void clearRecords(String tableName) {
        String sql = this.dialect.clearTable(tableName);
        this.jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteRecords(String tableName, String splitPk, TableInfo tableInfo) {
        String sql = "delete from " + tableName + " where " + splitPk + ">=" + tableInfo.getStart().getPk()
                + " and " + splitPk + "<=" + tableInfo.getFinish().getPk();
    }

}
