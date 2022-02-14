package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.core.container.biz.SyncTaskBiz;
import com.gitee.pristine.core.wrapper.DataSourceWrapper;
import com.gitee.pristine.datasource.processor.DatabaseMetaAnalysisProcessor;
import com.gitee.pristine.domain.dto.CheckConnDTO;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.domain.vo.TableVO;
import com.gitee.pristine.web.service.SysMigrateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 公共的数据源连接控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:22
 * @description: 测试数据源是否可以连接
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    // 日志
    private Logger log = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private SyncTaskBiz syncTaskBiz;
    @Resource
    private SysMigrateService sysMigrateService;

    /**
     * 测试数据源是否可用
     * @param checkConnDTO 数据源必要参数
     * @return
     */
    @PostMapping("/conn/check")
    public ApiResponse checkConnection(@RequestBody @Validated CheckConnDTO checkConnDTO) {
        Connection connection = null;
        try {
            Class.forName(checkConnDTO.getDriverClassName());
            connection = DriverManager.getConnection(checkConnDTO.getJdbcUrl(),
                                    checkConnDTO.getUsername(), checkConnDTO.getPassword());
        }
        catch (ClassNotFoundException e)
        {
            // 数据源驱动错误
            return ApiResponse.failure("未找到该数据源驱动，请检查驱动是否正确");
        }
        catch (SQLException e)
        {
            // 无法链接数据源
            e.printStackTrace();

            return ApiResponse.failure("无法连接数据库，请检查连接参数是否正确");
        }
        finally
        {
            try {
                if (connection!=null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("close connection failed. cause by -> {}", e.getMessage());
            }
        }
        return ApiResponse.success().message("连接成功");
    }

    /**
     * 查询某个数据源下的所有表名
     * @param migrateId
     * @return
     */
    @GetMapping("/getTables")
    public ApiResponse getTables(Long migrateId) {
        Migrate migrate = sysMigrateService.getById(migrateId);
        if (migrate == null) {
            return ApiResponse.failure().message("迁移任务不存在");
        }
        DataSourceWrapper dataSource = syncTaskBiz.getDataSource(migrate.getOriginDsId());
        List<TableVO> tables = new DatabaseMetaAnalysisProcessor(dataSource.getDataSource())
                .analysisTables();
        return ApiResponse.success().message("查询成功").data(tables);
    }


}
