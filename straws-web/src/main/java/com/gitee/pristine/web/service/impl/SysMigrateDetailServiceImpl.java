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
        // ????????????????????????????????????
        boolean existTableName = checkExistTableName(entity.getId(), entity.getOriginTableName(), entity.getTargetTableName(), null);
        if (existTableName) {
            throw new ServiceException("????????????????????????");
        }
        // ??????????????????
        Migrate migrate = sysMigrateService.getById(entity.getMigrateId());
        if (migrate == null) {
            throw new ServiceException("???????????????????????????");
        }

        // ???????????????
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // ??????????????????????????????
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        TableMeta tableMeta = processor.analysisTableMeta(entity.getOriginTableName());
        // ???????????????????????????????????????
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());
        // ??????????????????sql
        String originTableSelectSql = originDbDialect.generateQuerySql(tableMeta.getName(), tableMeta.getStrColumns(), null, null);
        String targetTableInsertSql = targetDbDialect.generateInsertSql(tableMeta.getName(), tableMeta.getStrColumns());
        // ??????????????????????????????
        tableMeta.setName(entity.getTargetTableName());
        String targetTableCreateSql = targetDbDialect.createTable(tableMeta);
        // ??????
        MigrateDetail migrateDetail = new MigrateDetail();
        BeanUtils.copyProperties(entity, migrateDetail);
        migrateDetail.setOriginTableSelectSql(originTableSelectSql);
        migrateDetail.setTargetTableInsertSql(targetTableInsertSql);
        migrateDetail.setTargetTableCreateSql(targetTableCreateSql);
        migrateDetail.setCreateTime(new Date());
        // ???????????????
        return migrateDetailMapper.insertSelective(migrateDetail);
    }

    @Override
    @Transactional
    public int updateEntity(MigrateDetailDTO entity) {
        // ????????????????????????
        MigrateDetail var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("??????????????????????????????");
        }
        // ??????????????????????????????
        boolean existTableName = checkExistTableName(entity.getId(), entity.getOriginTableName(), entity.getTargetTableName(), entity.getId());
        if (existTableName) {
            throw new ServiceException("????????????????????????");
        }
        // ??????????????????
        Migrate migrate = sysMigrateService.getById(var1.getMigrateId());
        if (migrate == null) {
            throw new ServiceException("???????????????????????????");
        }

        // ???????????????
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // ??????????????????????????????
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        TableMeta tableMeta = processor.analysisTableMeta(var1.getOriginTableName());
        // ???????????????????????????????????????
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());
        // ??????????????????sql
        String originTableSelectSql = originDbDialect.generateQuerySql(tableMeta.getName(), tableMeta.getStrColumns(), null, null);
        String targetTableInsertSql = targetDbDialect.generateInsertSql(tableMeta.getName(), tableMeta.getStrColumns());
        String targetTableCreateSql = targetDbDialect.createTable(tableMeta);

        // ??????
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        var1.setOriginTableSelectSql(originTableSelectSql);
        var1.setTargetTableInsertSql(targetTableInsertSql);
        var1.setTargetTableCreateSql(targetTableCreateSql);
        // ???????????????
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
        // ??????????????????
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("???????????????????????????");
        }

        // ???????????????
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // ??????????????????????????????
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(originDsWrapper.getDataSource());
        SchemaMeta originSchemaMeta = processor.analysisSchemaMeta();
        // ???????????????????????????????????????
        DatabaseDialect originDbDialect = DatabaseDialectFactory.getInstance(originDsWrapper.getDatabase());
        DatabaseDialect targetDbDialect = DatabaseDialectFactory.getInstance(targetDsWrapper.getDatabase());

        // ????????????
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
        // ??????????????????
        clearDetails(migrateId);
        // ??????????????????
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
        // ??????????????????????????????
        MigrateDetail condition = new MigrateDetail();
        condition.setMigrateId(migrateId);
        migrateDetailMapper.delete(condition);
    }

    @Override
    @Transactional
    public void migrateTable(Long migrateId) {
        // ??????????????????
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("???????????????????????????");
        }
        // ???????????????
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        JdbcTemplate targetJdbc = new JdbcTemplate(targetDsWrapper.getDataSource());
        // ????????????
        List<MigrateDetail> details = getByMigrateId(migrateId);
        for (MigrateDetail detail : details) {
            // ???????????????
            if (detail.getIsCreateTable()) {
                // ???????????????
                targetJdbc.execute(detail.getTargetTableCreateSql());
            }
        }
    }

    @Override
    @Transactional
    @Async
    public void migrateData(Long migrateId) {
        // ??????????????????
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            throw new ServiceException("???????????????????????????");
        }
        // ???????????????
        DataSourceWrapper originDsWrapper = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        DataSourceWrapper targetDsWrapper = syncTaskBiz.getDataSource(migrate.getTargetDsId());
        // ????????????????????????
        DatabaseMetaAnalysisProcessor processor = new DatabaseMetaAnalysisProcessor(targetDsWrapper.getDataSource());
        SchemaMeta targetSchemaMeta = processor.analysisSchemaMeta();
        JdbcTemplate targetJdbc = new JdbcTemplate(targetDsWrapper.getDataSource());
        // ???????????????
        MigrateSynchronizer synchronizer = new MigrateSynchronizer(originDsWrapper.getDataSource(), targetDsWrapper.getDataSource());
        // ????????????
        List<MigrateDetail> details = getByMigrateId(migrateId);
        try {
            progressManager.putProgress(String.valueOf(migrateId), Boolean.TRUE);
            for (MigrateDetail detail : details) {
                // ?????????????????????
                String targetTableName = detail.getTargetTableName();
                boolean existTable = targetSchemaMeta.containTable(targetTableName);
                if (!existTable) {
                    // ???????????????
                    targetJdbc.execute(detail.getTargetTableCreateSql());
                } else {
                    // ???????????????
                    targetJdbc.execute("delete from " + targetTableName);
                }
                // ????????????
                synchronizer.syncData(detail);
            }
        } catch (SQLException e) {
            throw new ServiceException("??????????????????");
        } finally {
            progressManager.removeProgress(String.valueOf(migrateId));
        }
    }

    /**
     * ???????????????????????????????????????
     * @param migrateId ????????????????????????
     * @param originTableName ???????????????
     * @param targetTableName ???????????????
     * @param excludeId ????????????????????????id, ??????????????????null
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
