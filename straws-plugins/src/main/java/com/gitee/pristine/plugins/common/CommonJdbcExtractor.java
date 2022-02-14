package com.gitee.pristine.plugins.common;

import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.processor.DatabaseMetaAnalysisProcessor;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPk;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.result.TableResult;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 通用的JDBC Extractor
 * @author Pristine Xu
 * @date 2022/1/19 3:10
 * @description:
 */
public abstract class CommonJdbcExtractor implements Extractor {

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;
    protected DatabaseMetaAnalysisProcessor databaseMetaAnalysisProcessor;
    protected DatabaseDialect dialect;
    protected Task task;

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
        // 根据数据库字典生成对应的查询sql
        this.task = task;
    }


    @Override
    public TableMeta getTableMeta() {
        return this.databaseMetaAnalysisProcessor
                .analysisTableMeta(this.task.getOriginTableName());
    }

    @Override
    public TableInfo getTableInfo(String tableName, String splitPk) {
        // 构建查询
        String querySQL = "select"
                + " max("+ splitPk +")     as max_split_pk,"
                + " count(" + splitPk + ") as total"
                + " from " + tableName;
        TableResult tableResult = this.jdbcTemplate.queryForObject(querySQL,
                                new BeanPropertyRowMapper<>(TableResult.class));
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTotal(tableResult.getTotal());
        tableInfo.setFinish(new SplitPk(tableResult.getMaxSplitPk(), 2));
        return tableInfo;
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


}
