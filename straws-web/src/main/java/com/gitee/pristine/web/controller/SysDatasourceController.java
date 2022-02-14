package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.DatasourceDTO;
import com.gitee.pristine.domain.dto.DeleteEntityDTO;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.web.service.SysDatasourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源管理控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:20
 * @description:
 */
@RestController
@RequestMapping("/sys/datasource")
public class SysDatasourceController extends BaseController{

    // 日志
    private Logger log = LoggerFactory.getLogger(SysDatasourceController.class);

    @Resource
    private SysDatasourceService sysDatasourceService;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        Datasource record = sysDatasourceService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        Datasource record = sysDatasourceService.getByName(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<Datasource> list = sysDatasourceService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Datasource> list = sysDatasourceService.getList(condition);
        PageInfo<Datasource> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated DatasourceDTO entity) {
        int rows = sysDatasourceService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated DatasourceDTO entity) {
        int rows = sysDatasourceService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(Long id) {
        int rows = sysDatasourceService.deleteById(id);
        return checkDeleteResult(rows);
    }

    /**
     * 在校验操作用户的密码后再执行删除操作
     * @param deleteDTO
     * @return
     */
    @PostMapping("/deleteAfterCheckPwd")
    public ApiResponse deleteAfterCheckPwd(@RequestBody @Validated DeleteEntityDTO deleteDTO) {
        int rows = sysDatasourceService.deleteAfterCheckPwd(deleteDTO);
        return checkDeleteResult(rows);
    }
}
