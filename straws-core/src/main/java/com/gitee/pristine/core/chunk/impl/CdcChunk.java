package com.gitee.pristine.core.chunk.impl;

import com.gitee.pristine.common.utils.BytesUtil;
import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.core.chunk.DelayRetryableChunk;
import com.gitee.pristine.core.chunk.conf.CdcEntity;
import com.gitee.pristine.core.chunk.conf.ChunkConfig;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.result.Result;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.datasource.split.SplitPkType;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;
import com.gitee.pristine.spi.result.SyncResult;
import com.gitee.pristine.spi.statis.Statistics;
import com.gitee.pristine.spi.transformer.Transformer;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CdcChunk 逻辑完全不同，需要重写同步逻辑
 * @author Pristine Xu
 * @date 2022/1/19 1:26
 * @description:
 *  1. 目前CDC模式下只支持单个Chunk工作，以便发生异常时对数据进行回滚操作
 *  2. CDC 目前暂不支持 数据转换
 */
public class CdcChunk extends DelayRetryableChunk {

    private final Extractor extractor;
    private final Loader loader;
    private final List<Transformer> transformers;
    private final Statistics statistics;
    private final SplitPkRange splitPkRange;
    private final TransactionTemplate transactionTemplate;
    private final ChunkConfig chunkConfig;

    public CdcChunk(Extractor extractor, Loader loader, List<Transformer> transformers, Statistics statistics, SplitPkRange splitPkRange, TransactionTemplate transactionTemplate, ChunkConfig chunkConfig) {
        this.extractor = extractor;
        this.loader = loader;
        this.transformers = transformers;
        this.statistics = statistics;
        this.splitPkRange = splitPkRange;
        this.transactionTemplate = transactionTemplate;
        this.chunkConfig = chunkConfig;
    }

    @Override
    public SyncResult service() throws Exception {
        long chunkStartTime = DateUtil.getTimeStamp();

        long totalBytes = 0;
        long totalCount = 0;
        long bufferBytes = 0;

        List<CdcEntity> entities = new ArrayList<>();

        List<Object[]> insertBufferCache = new ArrayList<>();
        List<Object[]> updateBufferCache = new ArrayList<>();
        List<Object[]> deleteBufferCache = new ArrayList<>();

        Result originResult = null;
        Result targetResult = null;
        TableMeta originTableMeta = null;

        SplitPkType splitPkType = chunkConfig.getSplitPkType();
        int splitPkIdxInColumns = chunkConfig.getSplitPkIdxInColumns();

        try {

            originResult = this.extractor.readRecords(this.splitPkRange);
            targetResult = this.loader.readRecords(this.splitPkRange);
            originTableMeta = this.extractor.getTableMeta();

            Object[] originRecord = getRecord(originResult.getResultSet());
            Object[] targetRecord = getRecord(targetResult.getResultSet());


            // 核心算法，借鉴Kettle的全表对比算法
            while (true) {
                long recordBytes = 0;
                if (originRecord==null && targetRecord==null) {
                    break;
                }
                else if (isNull(targetRecord)) {
                    // 数据为新增数据
                    insertBufferCache.add(originRecord);
                    recordBytes = BytesUtil.sizeOf(originRecord);
                    bufferBytes += recordBytes;
                    totalBytes += recordBytes;
                    ++ totalCount;
                    originRecord = getRecord(originResult.getResultSet());
                }
                else if (isNull(originRecord)) {
                    // 数据为删除数据
                    deleteBufferCache.add(targetRecord);
                    recordBytes = BytesUtil.sizeOf(targetRecord);
                    bufferBytes += recordBytes;
                    totalBytes += recordBytes;
                    ++ totalCount;
                    targetRecord = getRecord(targetResult.getResultSet());
                }
                else {
                    long compare = 0;
                    // 两者都不为空
                    if (splitPkType == SplitPkType.INT) {
                        int originId = (int) originRecord[splitPkIdxInColumns];
                        int targetId = (int) targetRecord[splitPkIdxInColumns];
                        compare = originId - targetId;
                    } else {
                        long originId = (long) originRecord[splitPkIdxInColumns];
                        long targetId = (long) targetRecord[splitPkIdxInColumns];
                        compare = originId - targetId;
                    }

                    if (compare == 0) {
                        // 比较内容
                        boolean equals = Arrays.equals(originRecord, targetRecord);
                        if (!equals) {
                            // 数据发生了更新
                            updateBufferCache.add(originRecord);
                            recordBytes = BytesUtil.sizeOf(originRecord);
                            bufferBytes += recordBytes;
                            totalBytes += recordBytes;
                            ++ totalCount;
                        }
                        originRecord = getRecord(originResult.getResultSet());
                        targetRecord = getRecord(targetResult.getResultSet());
                    }
                    else {
                        if (compare < 0) {
                            insertBufferCache.add(originRecord);
                            recordBytes = BytesUtil.sizeOf(originRecord);
                            bufferBytes += recordBytes;
                            totalBytes += recordBytes;
                            ++ totalCount;
                            originRecord = getRecord(originResult.getResultSet());
                        }
                        else {
                            deleteBufferCache.add(targetRecord);
                            recordBytes = BytesUtil.sizeOf(targetRecord);
                            bufferBytes += recordBytes;
                            totalBytes += recordBytes;
                            ++ totalCount;
                            targetRecord = getRecord(targetResult.getResultSet());
                        }
                    }
                }

                // 向 Statistics 汇报计数，如果达到限制速率则写入数据，刷新缓存
                boolean isRateLimitByBytes = this.statistics.incrBytesCounter(recordBytes);

                if (isRateLimitByBytes) {
                    entities.add(new CdcEntity(insertBufferCache, updateBufferCache, deleteBufferCache));
                    insertBufferCache.clear();
                    updateBufferCache.clear();
                    deleteBufferCache.clear();
                    bufferBytes = 0;
                }

            }

            // 操作剩余数据
            entities.add(new CdcEntity(insertBufferCache, updateBufferCache, deleteBufferCache));

            this.operateRecords(entities, originTableMeta);

            // 清空缓存
            insertBufferCache.clear();
            updateBufferCache.clear();
            deleteBufferCache.clear();
            bufferBytes = 0;

        } finally {
            if (originResult != null) {
                originResult.close();
            }
            if (targetResult != null) {
                targetResult.close();
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


    private Object[] getRecord(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Object[] rowData = null;
        if (rs.next()) {
            rowData = new Object[metaData.getColumnCount()];
            for (int j = 1; j <= metaData.getColumnCount(); ++j) {
                rowData[j - 1] = rs.getObject(j);
            }
        }
        return rowData;
    }

    private boolean isNull(Object record) {
        return record == null;
    }

    private void operateRecords(List<CdcEntity> entities, TableMeta tableMeta) {
        // 开启事务
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (CdcEntity entity : entities) {
                    long lastTimestamp = statistics.getTimestamp();
                    long interval = DateUtil.getTimeStamp() - lastTimestamp;
                    if (interval < 1000) {
                        try {
                            Thread.sleep(1000 - interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!entity.getInsertList().isEmpty()) {
                        loader.writeRecords(entity.getInsertList());
                    }
                    if (!entity.getUpdateList().isEmpty()) {
                        loader.updateRecords(tableMeta, entity.getUpdateList());
                    }
                    if (!entity.getDeleteList().isEmpty()) {
                        loader.deleteRecords(tableMeta, entity.getDeleteList());
                    }
                    statistics.resetCounter();
                    statistics.setTimestamp(DateUtil.getTimeStamp());
                }
            }
        });


    }
}
