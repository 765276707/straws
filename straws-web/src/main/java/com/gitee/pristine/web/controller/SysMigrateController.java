package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.MigrateDTO;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import com.gitee.pristine.web.service.SysMigrateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据迁移控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:20
 * @description:
 */
@RestController
@RequestMapping("/sys/migrate")
public class SysMigrateController extends BaseController{

    // 日志
    private Logger log = LoggerFactory.getLogger(SysMigrateController.class);

    @Resource
    private SysMigrateService sysMigrateService;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        Migrate record = sysMigrateService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        Migrate record = sysMigrateService.getByName(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<Migrate> list = sysMigrateService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Migrate> list = sysMigrateService.getList(condition);
        PageInfo<Migrate> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated({Insert.class}) MigrateDTO entity) {
        int rows = sysMigrateService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated({Update.class}) MigrateDTO entity) {
        int rows = sysMigrateService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(Long id) {
        int rows = sysMigrateService.deleteById(id);
        return checkDeleteResult(rows);
    }

}
