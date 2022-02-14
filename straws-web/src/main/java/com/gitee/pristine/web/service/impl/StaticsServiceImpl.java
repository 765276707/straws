package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.domain.vo.StaticsVO;
import com.gitee.pristine.web.mapper.DatasourceMapper;
import com.gitee.pristine.web.mapper.MigrateMapper;
import com.gitee.pristine.web.mapper.ScriptMapper;
import com.gitee.pristine.web.mapper.TaskMapper;
import com.gitee.pristine.web.service.StaticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/7 9:06
 * @description:
 */
@Service
public class StaticsServiceImpl implements StaticsService {

    @Resource
    private DatasourceMapper datasourceMapper;
    @Resource
    private ScriptMapper scriptMapper;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private MigrateMapper migrateMapper;

    @Override
    public List<StaticsVO> getStatics() {
        // 查询数据源统计
        List<Datasource> datasources = datasourceMapper.selectAll();
        StaticsVO dsSV = new StaticsVO("el-icon-coin", "数据源", String.valueOf(datasources.size()),
                "当前", "个数据源", "");

        // 查询脚本统计
        List<Script> scripts = scriptMapper.selectAll();
        StaticsVO scSV = new StaticsVO("el-icon-cpu", "转换器脚本", String.valueOf(scripts.size()),
                "当前", "个脚本", "");

        // 查询定时任务统计
        List<Task> tasks = taskMapper.selectAll();
        StaticsVO tkSV = new StaticsVO("el-icon-time", "定时同步任务", String.valueOf(tasks.size()),
                "当前", "个任务", "");

        // 查询迁移任务统计
        List<Migrate> migrates = migrateMapper.selectAll();
        StaticsVO mrSV = new StaticsVO("el-icon-guide", "迁移任务", String.valueOf(migrates.size()),
                "当前", "个任务", "");

        List<StaticsVO> staticsVOS = new LinkedList<>();
        staticsVOS.add(dsSV);
        staticsVOS.add(scSV);
        staticsVOS.add(tkSV);
        staticsVOS.add(mrSV);
        return staticsVOS;
    }
}
