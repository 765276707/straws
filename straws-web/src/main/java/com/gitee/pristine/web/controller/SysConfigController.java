package com.gitee.pristine.web.controller;

import com.gitee.pristine.aop.log.anno.AopLog;
import com.gitee.pristine.aop.log.constant.OpType;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.dto.ConfigDTO;
import com.gitee.pristine.domain.entity.Config;
import com.gitee.pristine.web.service.SysConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统配置
 * @author Pristine Xu
 * @date 2022/2/7 13:31
 * @description: 一些用户可以设置的系统性全局配置
 */
@RestController
@RequestMapping("/sys/conf")
public class SysConfigController extends BaseController{

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 获取配置
     * @return
     */
    @GetMapping("/getDefault")
    public ApiResponse getDefault() {
        Config config = sysConfigService.getDefault();
        return ApiResponse.success().message("查询成功").data(config);
    }

    /**
     * 更新配置
     * @param entity
     * @return
     */
    @AopLog(type = OpType.UPDATE, desc = "更新系统配置")
    @PutMapping("/updateDefault")
    public ApiResponse updateDefault(@RequestBody ConfigDTO entity) {
        int rows = sysConfigService.updateDefault(entity);
        return checkUpdateResult(rows);
    }

    /**
     * 重置配置
     * @return
     */
    @AopLog(type = OpType.UPDATE, desc = "重置系统配置")
    @PutMapping("/resetDefault")
    public ApiResponse resetDefault() {
        int rows = sysConfigService.resetDefault();
        return checkUpdateResult(rows);
    }
}
