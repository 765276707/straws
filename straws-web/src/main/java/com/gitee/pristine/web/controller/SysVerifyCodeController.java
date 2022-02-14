package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.vo.VerifyCodeVO;
import com.gitee.pristine.security.annotation.Anonymous;
import com.gitee.pristine.web.manager.VerifyCodeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 验证码控制器
 * @author Pristine Xu
 * @date 2022/2/4 11:11
 * @description:
 */
@RestController
public class SysVerifyCodeController {

    @Resource
    private VerifyCodeManager verifyCodeManager;

    /**
     * 获取验证码
     * @return
     */
    @Anonymous
    @GetMapping("/verifyCode")
    public ApiResponse sendVerifyCode() {
        // 生成验证码
        VerifyCodeVO verifyCodeVO = verifyCodeManager.createVerifyCode(4, 5);
        // 响应结果
        return ApiResponse.success().message("获取验证码成功").data(verifyCodeVO);
    }
}
