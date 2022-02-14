package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.env.ServerEnvHelper;
import com.gitee.pristine.common.env.model.GlobalEnv;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.entity.TaskLog;
import com.gitee.pristine.domain.vo.StaticsVO;
import com.gitee.pristine.web.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计数据
 * @author Pristine Xu
 * @date 2022/2/5 13:20
 * @description:
 */
@RestController
@RequestMapping("/statics")
public class StaticsController {

    @Resource
    private SysTaskLogService sysTaskLogService;
    @Resource
    private StaticsService staticsService;

    /**
     * 获取全局环境变量
     * @return
     */
    @GetMapping("/getGlobalEnv")
    public ApiResponse getGlobalEnv() {
        GlobalEnv globalEnv = ServerEnvHelper.getGlobalEnv();
        return ApiResponse.success().message("查询成功").data(globalEnv);
    }

    /**
     * 查询统计信息
     * @return
     */
    @GetMapping("/getStaticsList")
    public ApiResponse getStaticsList() {
        List<StaticsVO> staticsVOS = staticsService.getStatics();
        return ApiResponse.success().message("查询成功").data(staticsVOS);
    }

    /**
     * 获取最新的10条定时任务日志
     * @return
     */
    @GetMapping("/getLatestTaskLogs")
    public ApiResponse getLatestTaskLogs() {
        List<TaskLog> list = sysTaskLogService.getLatestTaskLogs();
        return ApiResponse.success().data(list);
    }
}
