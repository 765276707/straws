package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.ScriptDTO;
import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.domain.jsr303.group.Insert;
import com.gitee.pristine.domain.jsr303.group.Update;
import com.gitee.pristine.security.annotation.Anonymous;
import com.gitee.pristine.web.service.SysScriptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 脚本控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:20
 * @description:
 */
@RestController
@RequestMapping("/sys/script")
public class SysScriptController extends BaseController {

    // 日志
    private Logger log = LoggerFactory.getLogger(SysScriptController.class);

    @Resource
    private SysScriptService sysScriptService;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        Script record = sysScriptService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        Script record = sysScriptService.getByName(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<Script> list = sysScriptService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Script> list = sysScriptService.getList(condition);
        PageInfo<Script> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated({Insert.class}) ScriptDTO entity) {
        int rows = sysScriptService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated({Update.class}) ScriptDTO entity) {
        int rows = sysScriptService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(Long id) {
        int rows = sysScriptService.deleteById(id);
        return checkDeleteResult(rows);
    }

}
