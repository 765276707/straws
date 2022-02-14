package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.condition.TaskLogCondition;
import com.gitee.pristine.domain.entity.TaskLog;
import com.gitee.pristine.web.service.SysTaskLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时同步任务控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:21
 * @description:
 */
@RestController
@RequestMapping("/sys/task/log")
public class SysTaskLogController {

    @Resource
    private SysTaskLogService sysTaskLogService;

    @GetMapping("/getPage")
    public ApiResponse getPage(TaskLogCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<TaskLog> list = sysTaskLogService.getList(condition);
        PageInfo<TaskLog> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

}
