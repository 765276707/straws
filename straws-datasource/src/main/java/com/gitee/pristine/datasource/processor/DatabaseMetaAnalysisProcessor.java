package com.gitee.pristine.datasource.processor;

import com.gitee.pristine.datasource.constant.MetaLabel;
import com.gitee.pristine.datasource.ex.DatabaseMetaAnalysisException;
import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.meta.SchemaMeta;
import com.gitee.pristine.datasource.meta.TableMeta;

import com.gitee.pristine.domain.vo.TableVO;
import com.microsoft.sqlserver.jdbc.SQLServerDatabaseMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库Meta解析处理器
 * @author Pristine Xu
 * @date 2022/1/13 19:31
 * @description:
 */
public class DatabaseMetaAnalysisProcessor {

    // 日志
    private static Logger log = LoggerFactory.getLogger(DatabaseMetaAnalysisProcessor.class);
    // 待解析的数据源
    private final DataSource dataSource;

    public DatabaseMetaAnalysisProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SchemaMeta analysisSchemaMeta() throws DatabaseMetaAnalysisException {
        SchemaMeta schemaMeta = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            // 获取常用参数
            String schemaName = connection.getSchema();
            String catalog    = connection.getCatalog();
            DatabaseMetaData dbMetaData = connection.getMetaData();

            // 获取产品和驱动信息
            String productName    = dbMetaData.getDatabaseProductName();
            String productVersion = dbMetaData.getDatabaseProductVersion();
            String driverName     = dbMetaData.getDriverName();
            String driverVersion  = dbMetaData.getDriverVersion();

            // 解析表元数据
            List<TableMeta> tables = new ArrayList<>();
            ResultSet _tableRS = dbMetaData.getTables(catalog, schemaName, "%", new String[]{"TABLE"});
            while (_tableRS.next()) {
                // 获取当前表名
                String tableName = _tableRS.getString(MetaLabel.Table.TABLE_NAME);

                // 解析主键元数据
                ResultSet _primaryKeyRS = dbMetaData.getPrimaryKeys(catalog, schemaName, tableName);
                List<String> pks = new ArrayList<>();
                while (_primaryKeyRS.next()) {
                    String pkColumnName = _primaryKeyRS.getString(MetaLabel.PrimaryKey.COLUMN_NAME);
                    pks.add(pkColumnName);
                }

                // 解析列信息
                ResultSet _columnRS = dbMetaData.getColumns(catalog, schemaName, tableName, "%");
                List<ColumnMeta> columns = new ArrayList<>();
                while (_columnRS.next()) {
                    // 创建列定义
                    String  columnName             = _columnRS.getString(MetaLabel.Column.COLUMN_NAME);
                    String  columnTypeId           = _columnRS.getString(MetaLabel.Column.DATA_TYPE);
                    String  columnTypeName         = _columnRS.getString(MetaLabel.Column.TYPE_NAME);
                    boolean columnIsNullable       = "YES".equalsIgnoreCase(_columnRS.getString(MetaLabel.Column.IS_NULLABLE));
                    boolean columnIsAutoIncrement  = "YES".equalsIgnoreCase(_columnRS.getString(MetaLabel.Column.IS_AUTOINCREMENT));
                    int     columnLength           = _columnRS.getInt(MetaLabel.Column.COLUMN_SIZE);
                    String  columnDefaultValue     = _columnRS.getString(MetaLabel.Column.COLUMN_DEF);
                    String  columnRemarks          = _columnRS.getString(MetaLabel.Column.REMARKS);
                    int     columnOrdinalPosition  = _columnRS.getInt(MetaLabel.Column.ORDINAL_POSITION);
                    int     columnDecimalDigits    = _columnRS.getInt(MetaLabel.Column.DECIMAL_DIGITS);
                    int     columnCharOctetLength  = _columnRS.getInt(MetaLabel.Column.CHAR_OCTET_LENGTH);
                    boolean isPrimaryKey = pks.contains(columnName);
                    columns.add(new ColumnMeta(
                            columnName, columnTypeId, columnTypeName, isPrimaryKey, columnIsAutoIncrement, columnIsNullable,
                            columnLength, columnDefaultValue, columnDecimalDigits, columnCharOctetLength, columnOrdinalPosition, columnRemarks
                    ));
                }
                tables.add(new TableMeta(schemaName, tableName, pks, columns));
            }

            schemaMeta = new SchemaMeta(schemaName, productVersion, driverName, driverVersion, "UTF-8", tables);

        } catch (SQLException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw new DatabaseMetaAnalysisException("解析数据库元数据的时候错误", e);
        } finally {
            try {
                if (connection!=null && connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                if (log.isErrorEnabled()) {
                    log.error(String.format("关闭数据源链接时发生异常: %s", e.getMessage()));
                }
                e.printStackTrace();
            }
        }
        return schemaMeta;
    }


    public TableMeta analysisTableMeta(String tableName) throws DatabaseMetaAnalysisException{
        TableMeta tableMeta = null;
        Connection connection = null;
        try {
            // 获取常用参数
            connection = dataSource.getConnection();
            DatabaseMetaData dbMetaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            String schemaName = connection.getSchema();
            String databaseProductName = dbMetaData.getDatabaseProductName();

            // 解析主键元数据
            ResultSet _primaryKeyRS = dbMetaData.getPrimaryKeys(catalog, schemaName, tableName);
            List<String> pks = new ArrayList<>();
            while (_primaryKeyRS.next()) {
                String pkColumnName = _primaryKeyRS.getString(MetaLabel.PrimaryKey.COLUMN_NAME);
                pks.add(pkColumnName);
            }

            // 解析列信息
            ResultSet _columnRS = dbMetaData.getColumns(catalog, schemaName, tableName, "%");
            List<ColumnMeta> columns = new ArrayList<>();
            while (_columnRS.next()) {
                // 创建列定义
                String   columnName            = _columnRS.getString(MetaLabel.Column.COLUMN_NAME);
                String   columnTypeId          = _columnRS.getString(MetaLabel.Column.DATA_TYPE);
                String   columnTypeName        = _columnRS.getString(MetaLabel.Column.TYPE_NAME);
                boolean  columnIsNullable      = "YES".equalsIgnoreCase(_columnRS.getString(MetaLabel.Column.IS_NULLABLE));
                boolean  columnIsAutoIncrement = "YES".equalsIgnoreCase(_columnRS.getString(MetaLabel.Column.IS_AUTOINCREMENT));
                int      columnLength          = _columnRS.getInt(MetaLabel.Column.COLUMN_SIZE);
                String   columnDefaultValue    = _columnRS.getString(MetaLabel.Column.COLUMN_DEF);
                String   columnRemarks         = _columnRS.getString(MetaLabel.Column.REMARKS);
                int      columnOrdinalPosition = _columnRS.getInt(MetaLabel.Column.ORDINAL_POSITION);
                int      columnDecimalDigits   = _columnRS.getInt(MetaLabel.Column.DECIMAL_DIGITS);
                int      columnCharOctetLength = _columnRS.getInt(MetaLabel.Column.CHAR_OCTET_LENGTH);
                boolean isPrimaryKey = pks.contains(columnName);
                // SQL Server需要对默认字段做额外处理
                if ("Microsoft SQL Server".equalsIgnoreCase(databaseProductName)) {
                    if (columnDefaultValue != null) {
                        columnDefaultValue = trimColumnDefaultValue(columnDefaultValue);
                    }
                }
                columns.add(new ColumnMeta(
                        columnName, columnTypeId, columnTypeName, isPrimaryKey, columnIsAutoIncrement, columnIsNullable,
                        columnLength, columnDefaultValue, columnDecimalDigits, columnCharOctetLength, columnOrdinalPosition, columnRemarks
                ));
            }
            tableMeta = new TableMeta(schemaName, tableName, pks, columns);
        } catch (SQLException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw new DatabaseMetaAnalysisException(String.format("解析%s表元数据的时候错误", tableName), e);
        } finally {
            try {
                if (connection!=null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                if (log.isErrorEnabled()) {
                    log.error(String.format("关闭数据源链接时发生异常: %s", e.getMessage()));
                }
                e.printStackTrace();
            }
        }
        return tableMeta;
    }


    public List<TableVO> analysisTables() throws DatabaseMetaAnalysisException {
        Connection connection = null;
        List<TableVO> tables = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            // 获取常用参数
            String schemaName = connection.getSchema();
            String catalog    = connection.getCatalog();
            DatabaseMetaData dbMetaData = connection.getMetaData();

            // 解析表元数据
            ResultSet _tableRS = dbMetaData.getTables(catalog, schemaName, "%", new String[]{"TABLE"});
            while (_tableRS.next()) {
                // 获取当前表名
                String tableName = _tableRS.getString(MetaLabel.Table.TABLE_NAME);

                // 解析主键元数据
                tables.add(new TableVO(schemaName, tableName));
            }

        } catch (SQLException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            throw new DatabaseMetaAnalysisException("解析数据库元数据的时候错误", e);
        } finally {
            try {
                if (connection!=null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                if (log.isErrorEnabled()) {
                    log.error(String.format("关闭数据源链接时发生异常: %s", e.getMessage()));
                }
                e.printStackTrace();
            }
        }
        return tables;
    }

    private String trimColumnDefaultValue(String remarks) {
        // 格式SqlServer的一些问题
        boolean needTrim = remarks.startsWith("(") && remarks.endsWith(")");
        if (needTrim) {
            return remarks.substring(1, remarks.length()-1);
        }
        return remarks;
    }

}
