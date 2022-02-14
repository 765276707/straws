package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.core.container.biz.SyncTaskBiz;
import com.gitee.pristine.core.factory.DatabaseDialectFactory;
import com.gitee.pristine.core.migrate.MigrateSynchronizer;
import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.datasource.ddl.DatabaseDialect;
import com.gitee.pristine.datasource.meta.SchemaMeta;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.processor.DatabaseMetaAnalysisProcessor;
import com.gitee.pristine.domain.condition.MigrateDetailCondition;
import com.gitee.pristine.domain.dto.MigrateDetailDTO;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.domain.entity.MigrateDetail;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.manager.ProgressManager;
import com.gitee.pristine.web.mapper.MigrateDetailMapper;
import com.gitee.pristine.web.service.SysMigrateDetailService;
import com.gitee.pristine.web.service.SysMigrateService;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pristine Xu
 * @date 2022/1/19 5:31
 * @description:
 */
@Service
public class SysMigrateDetailServiceImpl implements SysMigrateDetailService {

    @Resource
    private MigrateDetailMapper migrateDetailMapper;
    @Resource
    private SysMigrateService sysMigrateService;
    @Resource
    private SyncTaskBiz syncTaskBiz;
    @Resource
    private ProgressManager progressManager;

    @Override
    public MigrateDetail getById(Long id) {
        return migrateDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MigrateDetail> getList(MigrateDetailCondition condition) {
        Example example = new Example(MigrateDetail.class);
        example.createCriteria()
                .andEqualTo("migrateId", condition.getMigrateId());
        return migrateDetailMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public int insertEntity(MigrateDetailDTO entity) {
        // 判断两个表名是否已经存在
        boolean existTableName = checkExistTableName(entity.getId(), entity.getOriginTableName(), entity.getTargetTableName(), null);
        if (existTableName) {
            throw new ServiceException("已存在相同表映射");
        }
        // 查询所属任务
        Migrate migrate = sysMigrateService.getById(entity.getMigrateId());
        if (migrate == null) {
            throw new ServiceException("所属迁移任务不存在");
        }

        // 获取数据源
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // 解析源头数据源的信息
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        TableMeta tableMeta = processor.analysisTableMeta(entity.getOriginTableName());
        // 获取目标数据源的数据库字典
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());
        // 重新生成相关sql
        String originTableSelectSql = originDbDialect.generateQuerySql(tableMeta.getName(), tableMeta.getStrColumns(), null, null);
        String targetTableInsertSql = targetDbDialect.generateInsertSql(tableMeta.getName(), tableMeta.getStrColumns());
        // 重新设置目标表的名称
        tableMeta.setName(entity.getTargetTableName());
        String targetTableCreateSql = targetDbDialect.createTable(tableMeta);
        // 赋值
        MigrateDetail migrateDetail = new MigrateDetail();
        BeanUtils.copyProperties(entity, migrateDetail);
        migrateDetail.setOriginTableSelectSql(originTableSelectSql);
        migrateDetail.setTargetTableInsertSql(targetTableInsertSql);
        migrateDetail.setTargetTableCreateSql(targetTableCreateSql);
        migrateDetail.setCreateTime(new Date());
        // 插入数据库
        return migrateDetailMapper.insertSelective(migrateDetail);
    }

    @Override
    @Transactional
    public int updateEntity(MigrateDetailDTO entity) {
        // 判断记录是否存在
        MigrateDetail var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("修改的迁移任务不存在");
        }
        // 判断名称是否已被使用
        boolean existTableName = checkExistTableName(entity.getId(), entity.getOriginTableName(), entity.getTargetTableName(), entity.getId());
        if (existTableName) {
            throw new ServiceException("已存在相同表映射");
        }
        // 查询所属任务
        Migrate migrate = sysMigrateService.getById(var1.getMigrateId());
        if (migrate == null) {
            throw new ServiceException("所属迁移任务不存在");
        }

        // 获取数据源
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // 解析源头数据源的信息
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        TableMeta tableMeta = processor.analysisTableMeta(var1.getOriginTableName());
        // 获取目标数据源的数据库字典
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());
        // 重新生成相关sql
        String originTableSelectSql = originDbDialect.generateQuerySql(tableMeta.getName(), tableMeta.getStrColumns(), null, null);
        String targetTableInsertSql = targetDbDialect.generateInsertSql(tableMeta.getName(), tableMeta.getStrColumns());
        String targetTableCreateSql = targetDbDialect.createTable(tableMeta);

        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        var1.setOriginTableSelectSql(originTableSelectSql);
        var1.setTargetTableInsertSql(targetTableInsertSql);
        var1.setTargetTableCreateSql(targetTableCreateSql);
        // 更新数据库
        return migrateDetailMapper.updateByPrimaryKeySelective(var1);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return migrateDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void createDetails(Long migrateId) {
        // 查询所属任务
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("所属迁移任务不存在");
        }

        // 获取数据源
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // 解析源头数据源的信息
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        SchemaMeta originSchemaMeta = processor.analysisSchemaMeta();
        // 获取目标数据源的数据库字典
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());

        // 生成列表
        List<TableMeta> tables = originSchemaMeta.getTables();
        List<MigrateDetail> details = tables.stream()
                .map(item -> {
                    MigrateDetail detail = new MigrateDetail();
                    detail.setMigrateId(migrateId);
                    detail.setOriginTableName(item.getName());
                    detail.setTargetTableName(item.getName());
                    detail.setOriginTableSelectSql(originDbDialect.generateQuerySql(item.getName(), item.getStrColumns(), null, null));
                    detail.setTargetTableInsertSql(targetDbDialect.generateInsertSql(item.getName(), item.getStrColumns()));
                    detail.setTargetTableCreateSql(targetDbDialect.createTable(item));
                    detail.setIsCreateTable(true);
                    detail.setCreateTime(new Date());
                    return detail;
                }).collect(Collectors.toList());
        // 清空旧的映射
        clearDetails(migrateId);
        // 保存新的映射
        for (MigrateDetail detail : details) {
            migrateDetailMapper.insertSelective(detail);
        }

//        List<List<MigrateDetail>> detailsList = CollectionUtil.split(details, 200);
//        for (List<MigrateDetail> var : detailsList) {
//            migrateDetailMapper.insertBatch(var);
//        }
    }

    @Override
    @Transactional
    public void clearDetails(Long migrateId) {
        // 清空该任务下所有映射
        MigrateDetail condition = new MigrateDetail();
        condition.setMigrateId(migrateId);
        migrateDetailMapper.delete(condition);
    }

    @Override
    @Transactional
    public void migrateTable(Long migrateId) {
        // 查询所属任务
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("所属迁移任务不存在");
        }
        // 获取数据源
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        JdbcTemplate targetJdbc = new JdbcTemplate(targetDsWrapper.getDataSource());
        // 同步数据
        List<MigrateDetail> details = getByMigrateId(migrateId);
        for (MigrateDetail detail : details) {
            // 是否创建表
            if (detail.getIsCreateTable()) {
                // 同步表结构
                targetJdbc.execute(detail.getTargetTableCreateSql());
            }
        }
    }

    @Override
    @Transactional
    @Async
    public void migrateData(Long migrateId) {
        // 查询所属任务
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("所属迁移任务不存在");
        }
        // 获取数据源
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // 目标数据源的相关
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(targetDsWrapper.getDataSource());
        SchemaMeta targetSchemaMeta = processor.analysisSchemaMeta();
        JdbcTemplate targetJdbc = new JdbcTemplate(targetDsWrapper.getDataSource());
        // 构建同步器
        MigrateSynchronizer synchronizer = new MigrateSynchronizer(originDsWrapper.getDataSource(), targetDsWrapper.getDataSource());
        // 同步数据
        List<MigrateDetail> details = getByMigrateId(migrateId);
        try {
            progressManager.putProgress(String.valueOf(migrateId), Boolean.TRUE);
            for (MigrateDetail detail : details) {
                // 判断表是否存在
                String targetTableName = detail.getTargetTableName();
                boolean existTable = targetSchemaMeta.containTable(targetTableName);
                if (!existTable) {
                    // 同步表结构
                    targetJdbc.execute(detail.getTargetTableCreateSql());
                } else {
                    // 清空表数据
                    targetJdbc.execute("delete from " + targetTableName);
                }
                // 同步数据
                synchronizer.syncData(detail);
            }
        } catch (SQLException e) {
            throw new ServiceException("同步数据失败");
        } finally {
            progressManager.removeProgress(String.valueOf(migrateId));
        }
    }

    /**
     * 判断已经存在相同的表名映射
     * @param migrateId 所属迁移任务编号
     * @param originTableName 源头表表名
     * @param targetTableName 目标表表名
     * @param excludeId 需要排除掉的映射id, 如果没有则填null
     * @return
     */
    private boolean checkExistTableName(Long migrateId, String originTableName, String targetTableName, Long excludeId) {
        int rows = migrateDetailMapper.checkExistTableName(migrateId, originTableName, targetTableName, excludeId);
        return rows > 0;
    }

    private List<MigrateDetail> getByMigrateId(Long migrateId) {
        Example example = new Example(MigrateDetail.class);
        example.createCriteria()
                .andEqualTo("migrateId", migrateId);
        return migrateDetailMapper.selectByExample(example);
    }
}
