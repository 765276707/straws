package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.entity.Dict;
import com.gitee.pristine.web.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公共的字典控制器
 * @author Pristine Xu
 * @date 2022/1/19 5:24
 * @description: 提供字典类型的数据的获取接口
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    // 日志
    private Logger log = LoggerFactory.getLogger(SysDictController.class);

    @Resource
    private SysDictService sysDictService;

    /**
     * 根据类型查询对应的字典列表
     * @param dictType 字典类型
     * @return
     */
    @GetMapping("/getList")
    public ApiResponse getList(String dictType) {
        List<Dict> list = sysDictService.getList(dictType);
        return ApiResponse.success().data(list);
    }

}
