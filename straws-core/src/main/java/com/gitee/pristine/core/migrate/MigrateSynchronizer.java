package com.gitee.pristine.core.migrate;

import ch.qos.logback.classic.db.names.TableName;
import com.gitee.pristine.common.utils.BytesUtil;
import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.MigrateDetail;
import com.gitee.pristine.scripts.groovy.GroovyExecutor;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;
import com.gitee.pristine.spi.result.SyncResult;
import com.gitee.pristine.spi.transformer.Transformer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据迁移同步器
 * @author Pristine Xu
 * @date 2022/1/20 3:40
 * @description: 目前只支持FULL模式的同步器
 */
public class MigrateSynchronizer {

    private JdbcTemplate targetJdbc;
    private DataSource originDS;
    private DataSource targetDS;

    private MigrateSynchronizer() {}

    public MigrateSynchronizer(DataSource originDS, DataSource targetDS) {
        this.originDS = originDS;
        this.targetDS = targetDS;
        this.targetJdbc = new JdbcTemplate(targetDS);
    }

    public void syncData(MigrateDetail detail) throws SQLException {
        // 初始化过滤器
        String transformers = detail.getTransformers();
        List<Transformer> transforms = new LinkedList<>();
        if (transformers!=null && !"".equals(transformers)) {
            String[] tfKeys = transformers.split(",");
            for (String tfKey : tfKeys) {
                Transformer transformer = GroovyExecutor.getScript(tfKey, Transformer.class);
                transforms.add(transformer);
            }
        }
        // 统计参数
        long bufferBytes = 0;
        long totalBytes = 0;
        long totalCount = 0;
        // 缓冲池
        List<Object[]> bufferCache = new ArrayList<>();
        // Origin Table 查询结果集
        Result result = null;

        try
        {
            // 查询数据
            result = readRecords(detail.getOriginTableSelectSql());

            // 遍历结果集
            ResultSet rs = result.getResultSet();
            while (rs.next()) {
                Object[] record = getRecord(rs);
                long recordBytes = BytesUtil.sizeOf(record);

                // 数据转换，注意过滤完的数据可能会为null
                for (Transformer transform : transforms) {
                    record = transform.transfer(record);
                    if (record == null) {
                        break;
                    }
                }

                // 判断 record 状态，不为null则添加到缓冲池
                if (record != null) {
                    bufferCache.add(record);
                    bufferBytes += recordBytes;
                }
                totalBytes += recordBytes;
                ++ totalCount;

                // 缓冲区的字节容量达到限定值之后，写入数据库，刷新缓冲区
                if (bufferBytes > 100000) {
                    writeRecords(detail.getTargetTableName(), detail.getTargetTableInsertSql(), bufferCache);
                    bufferCache.clear();
                    bufferBytes = 0;
                }
            }

            // 写入缓冲区中剩余的数据
            if (!bufferCache.isEmpty()) {
                writeRecords(detail.getTargetTableName(), detail.getTargetTableInsertSql(), bufferCache);
                bufferCache.clear();
                bufferBytes = 0;
            }

        }
        finally
        {
            // 关闭连接资源
            if (result != null) {
                result.close();
            }
        }

    }

    /**
     * 读取数据
     * @param rs
     * @return
     * @throws SQLException
     */
    private Object[] getRecord(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Object[] rowData = new Object[metaData.getColumnCount()];
        for (int j = 1; j <= metaData.getColumnCount(); ++j) {
            rowData[j - 1] = rs.getObject(j);
        }
        return rowData;
    }

    /**
     * 写入数据
     * @param records
     */
    private void writeRecords(String targetTableName, String insertSql, List<Object[]> records) {
        this.targetJdbc.update("SET IDENTITY_INSERT "+targetTableName+" ON");
        this.targetJdbc.batchUpdate(insertSql, records);
        this.targetJdbc.update("SET IDENTITY_INSERT "+targetTableName+" OFF");
    }

    private Result readRecords(String selectSql) throws SQLException {
        Connection connection = this.originDS.getConnection();
        Statement statement = connection.createStatement();
        statement.setFetchSize(1000);
        statement.executeQuery(selectSql);
        ResultSet resultSet = statement.executeQuery(selectSql);
        return new Result(resultSet, statement, connection);
    }
}
