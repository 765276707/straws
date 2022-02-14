package com.gitee.pristine.web.initialization;

import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.web.manager.DataSourceManager;
import com.gitee.pristine.web.mapper.DatasourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源初始化
 * @author Pristine Xu
 * @date 2022/1/19 9:56
 * @description: 在系统启动的时候加载已经存在在数据库中的数据源进行初始化
 */
@Component
public class InitializeDataSourceRunner implements CommandLineRunner {

    private final Logger log  = LoggerFactory.getLogger(InitializeDataSourceRunner.class);

    @Resource
    private DataSourceManager dataSourceManager;
    @Resource
    private DatasourceMapper datasourceMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查询表中的已存在的所有数据源记录
        List<Datasource> datasources = datasourceMapper.selectAll();
        // 初始化并存入缓存
        for (Datasource datasource : datasources) {
            dataSourceManager.initDataSourceWithEntity(datasource);
        }
        // 打印成功日志
        log.info("[Straws] Successfully loaded and init {} data sources", datasources.size());
    }

}
