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
 * 默认的同步容器
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

    // 初始化需要的业务类
    protected SyncTaskBiz syncTaskBiz;

    // Chunk内所需要
    protected Extractor extractor;
    protected Loader loader;
    protected List<Transformer> transformers;
    protected Statistics statistics;
    protected TransactionTemplate targetTransactionTemplate;

    // 容器内参数
    private Task task;
    private Synchronous synchronous;
    private TableInfo tableInfo;

    // 容器每次启动时间和结束时间
    private Date startTime;
    private Date finishTime;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SyncResult result = null;
        startTime = new Date();
        finishTime = null;
        try {
            // 获取JobDataMap的数据
            JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
            long taskId = dataMap.getLong(JobMapKey.TASK_ID);
            this.applicationContext = (ApplicationContext) dataMap.get(JobMapKey.SPRING_IOC);
            // 执行初始化操作
            this.init(taskId);
            // 执行任务切分
            List<Chunk> chunks = this.split();
            // 执行数据同步
            result = this.sync(chunks);
            // 正常结束时间
            finishTime = new Date();
            // 执行后置处理
            this.post(result);
        }
        catch (Exception e) {
            // 中断截至时间
            finishTime = new Date();
            // 失败处理
            log.error("fallback: {}", e.getMessage());

            this.fallback(e);
            // 打印或抛出异常
            if (log.isErrorEnabled()) {
                log.error("task - {} - execute failed in job.", task.getName());
            } else {
                throw new JobExecutionException(e);
            }
        }
        finally {
            if (result != null) {
                // 控制台打印结果
                SyncResultTool.printInConsole(result, task, startTime, finishTime);
            }
        }
    }


    @Override
    public void init(long taskId) throws JobExecutionException {
        // 获取 Biz
        syncTaskBiz = this.applicationContext.getBean(SyncTaskBiz.class);
        // 查询 Task
        task = syncTaskBiz.getById(taskId);
        if (task == null) {
            throw new JobExecutionException("未查询到相应的任务记录");
        }
        synchronous = Synchronous.toMode(task.getSyncMode());

        // 获取数据源
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(task.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(task.getTargetDsId());

        // 初始化 target 数据源的事务模板
        this.targetTransactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(targetDsWrapper.getDataSource()));

        // 初始化 Extractor
        extractor = ExtractorFactory.getInstance(originDsWrapper.getDatabase());
        extractor.setDataSource(originDsWrapper.getDataSource());
        extractor.setDatabaseDialect(DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase()));
        extractor.setTaskDetail(task);

        // 初始化 Loader
        loader = LoaderFactory.getInstance(targetDsWrapper.getDatabase());
        loader.setDataSource(targetDsWrapper.getDataSource());
        loader.setDatabaseDialect(DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase()));
        loader.setTaskDetail(task);

        // 初始化 Transformers
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

        // 初始化 Statistics
        statistics = new Statistics(task.getBytesPerSecond());
    }

    @Override
    public List<Chunk> split() {
        /**
         * INC 增量查询，每次查询据上一次的位置到最新数据
         * FULL 与 CDC 都是要查询全表，在此处相同
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

        // 未设置切分主键，则当作一个Chunk处理，则【chunksPerGroup】配置参数不起作用
        int chunksPerGroup = 1;
        if (!StringUtils.isEmpty(task.getSplitPk()))
        {
            chunksPerGroup = task.getWorkersPerGroup();
        }
        // 对要同步的数据进行切分
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
        // FULL模式下需要清空目标表数据
        if (synchronous == Synchronous.FULL) {
            loader.clearRecords(task.getTargetTableName());
        }
        // 执行同步
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
        // FULL 同步成功，更新Task记录，增加日志
        if (synchronous == Synchronous.INCR) {
            task.setUpdateTime(new Date());
            task.setLastMinSplitPk(0L);
            task.setLastMaxSplitPk(tableInfo.getFinish().getPk().longValue());
            syncTaskBiz.updateTaskAfterSync(task);
        }

        // 增加成功日志
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(task.getId());
        taskLog.setTaskName(task.getName());
        taskLog.setTaskStartTime(startTime);
        taskLog.setTaskFinishTime(finishTime);
        taskLog.setTaskResult(TaskResultStatus.SUCCESS.getStatus()); // 1:成功 2:失败
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
         * FULL 同步失败，则清空目标表
         * INCR 删除此处增量的数据
         * CDC  出现异常，会在Chunk内事务回滚，此处不需要做兜底操作
         */
//        if (synchronous == Synchronous.FULL)
//        {
//            loader.clearRecords(task.getTargetTableName());
//        }
//        else if (synchronous == Synchronous.INCR)
//        {
//            loader.deleteRecords(task.getTargetTableName(), task.getSplitPk(), tableInfo);
//        }

        // 添加失败日志
        if (task != null) {
            TaskLog taskLog = new TaskLog();
            taskLog.setTaskId(task.getId());
            taskLog.setTaskName(task.getName());
            taskLog.setTaskStartTime(startTime);
            taskLog.setTaskFinishTime(finishTime);
            taskLog.setTaskResult(TaskResultStatus.FAILURE.getStatus()); // 1:成功 2:失败
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
     * 根据不同模式生成对应的 Chunk
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
