package com.gitee.pristine.core.chunk.impl;

import com.gitee.pristine.common.utils.BytesUtil;
import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.core.chunk.DelayRetryableChunk;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;
import com.gitee.pristine.spi.result.SyncResult;
import com.gitee.pristine.spi.statis.Statistics;
import com.gitee.pristine.spi.transformer.Transformer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/1/18 19:21
 * @description:
 */
public class FullChunk extends DelayRetryableChunk {

    private final Extractor extractor;
    private final Loader loader;
    private final List<Transformer> transformers;
    private final Statistics statistics;
    private final SplitPkRange splitPkRange;

    public FullChunk(Extractor extractor, Loader loader, List<Transformer> transformers, Statistics statistics, SplitPkRange splitPkRange) {
        this.extractor = extractor;
        this.loader = loader;
        this.transformers = transformers;
        this.statistics = statistics;
        this.splitPkRange = splitPkRange;
    }

    @Override
    public SyncResult service() throws Exception {
        // 开始时间
        long chunkStartTime = DateUtil.getTimeStamp();
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
            result = this.extractor.readRecords(this.splitPkRange);

            // 遍历结果集
            ResultSet rs = result.getResultSet();
            while (rs.next()) {
                Object[] record = getRecord(rs);
                long recordBytes = BytesUtil.sizeOf(record);

                // 数据转换
                for (Transformer transformer : this.transformers) {
                    record = transformer.transfer(record);
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

                // 向 Statistics 汇报计数，如果达到限制速率则写入数据，刷新缓存
                boolean isRateLimitByBytes = this.statistics.incrBytesCounter(recordBytes);
                if (isRateLimitByBytes) {
                    writeRecords(bufferCache);
                    bufferCache.clear();
                    bufferBytes = 0;
                }
            }

            // 写入缓存池中剩余的数据
            if (!bufferCache.isEmpty()) {
                writeRecords(bufferCache);
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

        // 统计Chunk内执行结果
        long transferTime = DateUtil.getTimeStamp() - chunkStartTime;
        return SyncResult.collect()
                        .transferTime(transferTime)
                        .totalRecords(totalCount)
                        .totalBytes(totalBytes)
                        .bytesPerSecond(totalBytes / transferTime);
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
    private void writeRecords(List<Object[]> records) {
        // 写入数据
        long now = DateUtil.getTimeStamp();
        long timestamp = this.statistics.getTimestamp();
        long interval = now - timestamp;
        if (interval < 1000) {
            try {
                Thread.sleep(1000 - interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.statistics.resetCounter();
            }
        }
        this.loader.writeRecords(records);
        this.statistics.setTimestamp(DateUtil.getTimeStamp());
    }
}
