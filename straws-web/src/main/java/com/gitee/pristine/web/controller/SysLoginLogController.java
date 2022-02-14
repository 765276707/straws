package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.LoginLog;
import com.gitee.pristine.domain.entity.Oplog;
import com.gitee.pristine.web.service.SysLoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志
 * @author Pristine Xu
 * @date 2022/2/8 8:16
 * @description:
 */
@RestController
@RequestMapping("/sys/loginlog")
public class SysLoginLogController {

    @Resource
    private SysLoginLogService sysLoginLogService;

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<LoginLog> list = sysLoginLogService.getList(condition);
        PageInfo<LoginLog> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

}
