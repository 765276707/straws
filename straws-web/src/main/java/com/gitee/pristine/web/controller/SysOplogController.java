package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.domain.entity.Oplog;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.web.service.SysOplogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志
 * @author Pristine Xu
 * @date 2022/2/8 6:12
 * @description:
 */
@RestController
@RequestMapping("/sys/oplog")
public class SysOplogController {

    @Resource
    private SysOplogService sysOplogService;

    @GetMapping("/getList")
    public ApiResponse getList() {
        String username = SecurityContext.getContext().getUsername();
        List<Oplog> list = sysOplogService.getListByUsername(username);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Oplog> list = sysOplogService.getList(condition);
        PageInfo<Oplog> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

}
