package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.condition.MigrateDetailCondition;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.dto.MigrateDetailDTO;
import com.gitee.pristine.domain.entity.MigrateDetail;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import com.gitee.pristine.web.manager.ProgressManager;
import com.gitee.pristine.web.service.SysMigrateDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据迁移详情控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:21
 * @description:
 */
@RestController
@RequestMapping("/sys/migrate/detail")
public class SysMigrateDetailController extends BaseController{

    @Resource
    private SysMigrateDetailService sysMigrateDetailService;
    @Resource
    private ProgressManager progressManager;

    // 查询单个表的映射
    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        MigrateDetail record = sysMigrateDetailService.getById(id);
        return ApiResponse.success().data(record);
    }

    // 分页查询表的映射
    @GetMapping("/getPage")
    public ApiResponse getPage(MigrateDetailCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<MigrateDetail> list = sysMigrateDetailService.getList(condition);
        PageInfo<MigrateDetail> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    // 添加表的映射
    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated({Insert.class}) MigrateDetailDTO entity) {
        int rows = sysMigrateDetailService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    // 编辑表的映射
    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated({Update.class}) MigrateDetailDTO entity) {
        int rows = sysMigrateDetailService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    // 删除表的映射
    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(Long id) {
        int rows = sysMigrateDetailService.deleteById(id);
        return checkDeleteResult(rows);
    }

    // 生成表的映射
    @GetMapping("/createDetails")
    public ApiResponse createDetails(Long migrateId) {
        sysMigrateDetailService.createDetails(migrateId);
        return ApiResponse.success().message("生成映射成功");
    }

    // 清空表的映射
    @DeleteMapping("/clearDetails")
    public ApiResponse clearDetails(Long migrateId) {
        sysMigrateDetailService.clearDetails(migrateId);
        return ApiResponse.success().message("清空映射成功");
    }

    // 迁移表的结构
    @GetMapping("/migrateTable")
    public ApiResponse migrateTable(Long migrateId) {
        if (progressManager.getProgress(String.valueOf(migrateId)) != null) {
            return ApiResponse.success().message("已有迁移任务在后台运行，请稍候再试");
        }
        sysMigrateDetailService.migrateTable(migrateId);
        return ApiResponse.success().message("表结构迁移成功");
    }

    // 迁移表的数据，目前只支持全量同步，异步执行
    @GetMapping("/migrateData")
    public ApiResponse migrateData(Long migrateId) {
        if (progressManager.getProgress(String.valueOf(migrateId)) != null) {
            return ApiResponse.success().message("已有迁移任务在后台运行，请稍候再试");
        }
        sysMigrateDetailService.migrateData(migrateId);
        return ApiResponse.success().message("启动表数据迁移");
    }
}
