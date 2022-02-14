package com.gitee.pristine.web.controller;

import com.gitee.pristine.aop.log.anno.AopLog;
import com.gitee.pristine.aop.log.constant.OpType;
import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.CheckBeforeOperateDTO;
import com.gitee.pristine.domain.dto.DatasourceDTO;
import com.gitee.pristine.domain.dto.ModifyPasswordDTO;
import com.gitee.pristine.domain.dto.UserDTO;
import com.gitee.pristine.domain.entity.Datasource;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.context.SecurityContext;
import com.gitee.pristine.security.utils.ResponseUtil;
import com.gitee.pristine.web.config.SecurityConfig;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 管理员管理
 * @author Pristine Xu
 * @date 2022/1/23 6:50
 * @description:
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController{

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        User record = sysUserService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        User record = sysUserService.getByUsername(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByLogin")
    public ApiResponse getLogin() {
        String username = SecurityContext.getContext().getUsername();
        User record = sysUserService.getByUsername(username);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<User> list = sysUserService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<User> list = sysUserService.getList(condition);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @AopLog(type = OpType.INSERT, desc = "新增用户")
    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated UserDTO entity) {
        int rows = sysUserService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @AopLog(type = OpType.UPDATE, desc = "编辑用户")
    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated UserDTO entity) {
        int rows = sysUserService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @AopLog(type = OpType.UPDATE, desc = "更新密码")
    @PutMapping("/modifyPassword")
    public ApiResponse modifyPassword(@RequestBody @Validated ModifyPasswordDTO modifyPasswordDTO) {
        String username = SecurityContext.getContext().getUsername();
        if (!modifyPasswordDTO.getNewPassword().equals(modifyPasswordDTO.getRepPassword())) {
            throw new ServiceException("新密码与确认密码不一致");
        }
        int rows = sysUserService.modifyPassword(username, modifyPasswordDTO);
        return checkUpdateResult(rows);
    }

    @AopLog(type = OpType.UPDATE, desc = "重置密码")
    @PutMapping("/resetPassword")
    public ApiResponse resetPassword(@RequestBody @Validated CheckBeforeOperateDTO checkBeforeOperateDTO) {
        // 验证操作者的密码是否正确
        checkCurrentLoginUserPassword(checkBeforeOperateDTO.getPassword());
        // 验证通过，继续操作
        int rows = sysUserService.resetPassword(checkBeforeOperateDTO.getId());
        return checkUpdateResult(rows);
    }

    @AopLog(type = OpType.DELETE, desc = "删除用户")
    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(@RequestBody @Validated CheckBeforeOperateDTO checkBeforeOperateDTO) {
        // 验证操作者的密码是否正确
        checkCurrentLoginUserPassword(checkBeforeOperateDTO.getPassword());
        // 验证通过，继续操作
        int rows = sysUserService.deleteById(checkBeforeOperateDTO.getId());
        return checkDeleteResult(rows);
    }

}
