package com.gitee.pristine.core.container;

import com.gitee.pristine.common.constant.Synchronous;
import com.gitee.pristine.common.utils.BytesUtil;
import com.gitee.pristine.common.utils.DateUtil;
import com.gitee.pristine.common.utils.SpringUtil;
import com.gitee.pristine.core.chunk.conf.ChunkConfig;
import com.gitee.pristine.core.chunk.impl.CdcChunk;
import com.gitee.pristine.core.chunk.impl.FullChunk;
import com.gitee.pristine.core.chunk.impl.IncrChunk;
import com.gitee.pristine.core.constant.JobMapKey;
import com.gitee.pristine.core.constant.TaskResultStatus;
import com.gitee.pristine.core.container.biz.SyncTaskBiz;
import com.gitee.pristine.core.executor.ChunkThreadPoolExecutor;
import com.gitee.pristine.core.factory.DatabaseDialectFactory;
import com.gitee.pristine.core.factory.ExtractorFactory;
import com.gitee.pristine.core.factory.LoaderFactory;
import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.datasource.info.TableInfo;
import com.gitee.pristine.datasource.split.SplitPk;
import com.gitee.pristine.datasource.split.SplitPkRange;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.domain.entity.TaskLog;
import com.gitee.pristine.schedule.container.SyncContainer;
import com.gitee.pristine.scripts.groovy.GroovyExecutor;
import com.gitee.pristine.spi.extractor.Extractor;
import com.gitee.pristine.spi.loader.Loader;
import com.gitee.pristine.spi.result.SyncResult;
import com.gitee.pristine.spi.result.SyncResultTool;
import com.gitee.pristine.spi.spliter.PkSpliter;
import com.gitee.pristine.spi.statis.Statistics;
import com.gitee.pristine.spi.task.Chunk;
import com.gitee.pristine.spi.transformer.Transformer;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * ?????????????????????
 * @author Pristine Xu
 * @date 2022/1/18 20:53
 * @description:
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DefaultSyncContainer implements SyncContainer {

    private Logger log = LoggerFactory.getLogger(DefaultSyncContainer.class);

    // Spring IOC
    private ApplicationContext applicationContext;

    // ???????????????????????????
    protected SyncTaskBiz syncTaskBiz;

    // Chunk????????????
    protected Extractor extractor;
    protected Loader loader;
    protected List<Transformer> transformers;
    protected Statistics statistics;
    protected TransactionTemplate targetTransactionTemplate;

    // ???????????????
    private Task task;
    private Synchronous synchronous;
    private TableInfo tableInfo;

    // ???????????????????????????????????????
    private Date startTime;
    private Date finishTime;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SyncResult result = null;
        startTime = new Date();
        finishTime = null;
        try {
            // ??????JobDataMap?????????
            JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
            long taskId = dataMap.getLong(JobMapKey.TASK_ID);
            this.applicationContext = (ApplicationContext) dataMap.get(JobMapKey.SPRING_IOC);
            // ?????????????????????
            this.init(taskId);
            // ??????????????????
            List<Chunk> chunks = this.split();
            // ??????????????????
            result = this.sync(chunks);
            // ??????????????????
            finishTime = new Date();
            // ??????????????????
            this.post(result);
        }
        catch (Exception e) {
            // ??????????????????
            finishTime = new Date();
            // ????????????
            log.error("fallback: {}", e.getMessage());

            this.fallback(e);
            // ?????????????????????
            if (log.isErrorEnabled()) {
                log.error("task - {} - execute failed in job.", task.getName());
            } else {
                throw new JobExecutionException(e);
            }
        }
        finally {
            if (result != null) {
                // ?????????????????????
                SyncResultTool.printInConsole(result, task, startTime, finishTime);
            }
        }
    }


    @Override
    public void init(long taskId) throws JobExecutionException {
        // ?????? Biz
        syncTaskBiz = this.applicationContext.getBean(SyncTaskBiz.class);
        // ?????? Task
        task = syncTaskBiz.getById(taskId);
        if (task == null) {
            throw new JobExecutionException("?????????????????????????????????");
        }
        synchronous = Synchronous.toMode(task.getSyncMode());

        // ???????????????
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(task.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(task.getTargetDsId());

        // ????????? target ????????????????????????
        this.targetTransactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(targetDsWrapper.getDataSource()));

        // ????????? Extractor
        extractor = ExtractorFactory.getInstance(originDsWrapper.getDatabase());
        extractor.setDataSource(originDsWrapper.getDataSource());
        extractor.setDatabaseDialect(DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase()));
        extractor.setTaskDetail(task);

        // ????????? Loader
        loader = LoaderFactory.getInstance(targetDsWrapper.getDatabase());
        loader.setDataSource(targetDsWrapper.getDataSource());
        loader.setDatabaseDialect(DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase()));
        loader.setTaskDetail(task);

        // ????????? Transformers
        String transformsInTask = task.getTransforms();
        transformers = new LinkedList<>();
        if (transformsInTask != null) {
            String[] tfKeys = transformsInTask.split(",");
            for (String tfKey : tfKeys) {
                this.transformers.add(
                        GroovyExecutor.getScript(tfKey, Transformer.class)
                );
            }
        }

        // ????????? Statistics
        statistics = new Statistics(task.getBytesPerSecond());
    }

    @Override
    public List<Chunk> split() {
        /**
         * INC ???????????????????????????????????????????????????????????????
         * FULL ??? CDC ???????????????????????????????????????
         */
        if (synchronous == Synchronous.INCR)
        {
            Long lastMaxSplitPk = task.getLastMaxSplitPk();
            lastMaxSplitPk = lastMaxSplitPk==null?0:lastMaxSplitPk;
            tableInfo = extractor.getTableInfo(task.getOriginTableName(), task.getSplitPk());
            tableInfo.setStart(new SplitPk(lastMaxSplitPk, task.getSplitPkType()));
        }
        else
        {
            tableInfo = extractor.getTableInfo(task.getOriginTableName(), task.getSplitPk());
            tableInfo.setStart(new SplitPk(0, task.getSplitPkType()));
        }

        // ???????????????????????????????????????Chunk???????????????chunksPerGroup???????????????????????????
        int chunksPerGroup = 1;
        if (!StringUtils.isEmpty(task.getSplitPk()))
        {
            chunksPerGroup = task.getWorkersPerGroup();
        }
        // ?????????????????????????????????
        List<SplitPkRange> ranges = PkSpliter.config(tableInfo, chunksPerGroup).doSplit();
        List<Chunk> workers = new ArrayList<>(ranges.size());
        for (SplitPkRange range : ranges) {
            Chunk chunk = this.createChunkByMode(range);
            workers.add(chunk);
        }
        return workers;
    }

    @Override
    public SyncResult sync(List<Chunk> chunks) throws Exception {
        // FULL????????????????????????????????????
        if (synchronous == Synchronous.FULL) {
            loader.clearRecords(task.getTargetTableName());
        }
        // ????????????
        List<SyncResult> execRes = new ArrayList<>();
        List<Future<SyncResult>> futures
                = ChunkThreadPoolExecutor.getInstance().invokeAll(chunks);
        for (Future<SyncResult> future : futures) {
            SyncResult res = future.get();
            execRes.add(res);
        }
        return SyncResultTool.merge(execRes);
    }

    @Override
    public void post(SyncResult result) {
        // FULL ?????????????????????Task?????????????????????
        if (synchronous == Synchronous.INCR) {
            task.setUpdateTime(new Date());
            task.setLastMinSplitPk(0L);
            task.setLastMaxSplitPk(tableInfo.getFinish().getPk().longValue());
            syncTaskBiz.updateTaskAfterSync(task);
        }

        // ??????????????????
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(task.getId());
        taskLog.setTaskName(task.getName());
        taskLog.setTaskStartTime(startTime);
        taskLog.setTaskFinishTime(finishTime);
        taskLog.setTaskResult(TaskResultStatus.SUCCESS.getStatus()); // 1:?????? 2:??????
        taskLog.setTransferRecordCount(result.getTotalRecords());
        taskLog.setTransferRecordBytes(BytesUtil.humanReadableUnits(result.getTotalBytes()));
        taskLog.setTransferAverageTime(DateUtil.humanReadableTime(result.getTransferTime()));
        taskLog.setTransferAverageSpeed(BytesUtil.humanReadableUnits(result.getBytesPerSecond()));
        taskLog.setCreateTime(new Date());
        syncTaskBiz.addTaskLog(taskLog);
    }

    @Override
    public void fallback(Exception e) {
        /**
         * FULL ?????????????????????????????????
         * INCR ???????????????????????????
         * CDC  ?????????????????????Chunk????????????????????????????????????????????????
         */
//        if (synchronous == Synchronous.FULL)
//        {
//            loader.clearRecords(task.getTargetTableName());
//        }
//        else if (synchronous == Synchronous.INCR)
//        {
//            loader.deleteRecords(task.getTargetTableName(), task.getSplitPk(), tableInfo);
//        }

        // ??????????????????
        if (task != null) {
            TaskLog taskLog = new TaskLog();
            taskLog.setTaskId(task.getId());
            taskLog.setTaskName(task.getName());
            taskLog.setTaskStartTime(startTime);
            taskLog.setTaskFinishTime(finishTime);
            taskLog.setTaskResult(TaskResultStatus.FAILURE.getStatus()); // 1:?????? 2:??????
            taskLog.setTaskFailedReason(e.getMessage());
            taskLog.setTransferRecordCount(0L);
            taskLog.setTransferRecordBytes("0 b");
            taskLog.setTransferAverageTime("0 ms");
            taskLog.setTransferAverageSpeed("0 b/s");
            taskLog.setCreateTime(new Date());
            syncTaskBiz.addTaskLog(taskLog);
        }

    }

    /**
     * ????????????????????????????????? Chunk
     * @param range
     * @return
     */
    private Chunk createChunkByMode(SplitPkRange range) {
        if (synchronous == Synchronous.FULL)
        {
            return new FullChunk(this.extractor, this.loader, this.transformers, this.statistics, range);
        }
        else if (synchronous == Synchronous.INCR)
        {
            return new IncrChunk(this.extractor, this.loader, this.transformers, this.statistics, range);
        }
        else
        {
            String splitPk = this.task.getSplitPk();
            Integer splitPkType = this.task.getSplitPkType();
            String columns = this.task.getColumns();
            ChunkConfig chunkConfig = new ChunkConfig(splitPk, splitPkType, columns);
            return new CdcChunk(this.extractor, this.loader, this.transformers, this.statistics, range, this.targetTransactionTemplate, chunkConfig);
        }
    }
}
