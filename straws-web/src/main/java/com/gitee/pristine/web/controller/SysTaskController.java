package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.TaskDTO;
import com.gitee.pristine.domain.entity.Task;
import com.gitee.pristine.web.service.SysTaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时同步控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:21
 * @description:
 */
@RestController
@RequestMapping("/sys/task")
public class SysTaskController extends BaseController{

    // 日志
    private Logger log = LoggerFactory.getLogger(SysDatasourceController.class);

    @Resource
    private SysTaskService sysTaskService;
    @Resource
    private Scheduler scheduler;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        Task record = sysTaskService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        Task record = sysTaskService.getByName(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<Task> list = sysTaskService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Task> list = sysTaskService.getList(condition);
        PageInfo<Task> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated TaskDTO entity) {
        int rows = sysTaskService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated TaskDTO entity) {
        int rows = sysTaskService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(Long id) {
        int rows = sysTaskService.deleteById(id);
        return checkDeleteResult(rows);
    }

    // 暂停、恢复任务
    @GetMapping("/resumeOrPause")
    public ApiResponse resumeOrPauseTask(Long id) {
        sysTaskService.resumeOrPauseTask(id);
        return ApiResponse.success().message("操作成功");
    }

    // 触发一次任务
    @GetMapping("/trigger")
    public ApiResponse triggerTask(Long id) {
        sysTaskService.triggerTask(id);
        return ApiResponse.success().message("触发成功");
    }
}
