package com.gitee.pristine.web.controller;

import com.gitee.pristine.common.page.Page;
import com.gitee.pristine.common.result.ApiResponse;
import com.gitee.pristine.domain.base.PageCondition;
import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.CheckBeforeOperateDTO;
import com.gitee.pristine.domain.dto.RoleDTO;
import com.gitee.pristine.domain.entity.Role;
import com.gitee.pristine.web.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色控制器
 * @author Pristine Xu
 * @date 2022/2/5 1:13
 * @description:
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/getById")
    public ApiResponse getById(Long id) {
        Role record = sysRoleService.getById(id);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getByName")
    public ApiResponse getByName(String name) {
        Role record = sysRoleService.getByUsername(name);
        return ApiResponse.success().data(record);
    }

    @GetMapping("/getList")
    public ApiResponse getList(TextCondition condition) {
        List<Role> list = sysRoleService.getList(condition);
        return ApiResponse.success().data(list);
    }

    @GetMapping("/getPage")
    public ApiResponse getPage(TextCondition condition, PageCondition page) {
        PageHelper.startPage(page);
        List<Role> list = sysRoleService.getList(condition);
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return ApiResponse.success().data(Page.toData(pageInfo));
    }

    @PostMapping("/insertEntity")
    public ApiResponse insertEntity(@RequestBody @Validated RoleDTO entity) {
        int rows = sysRoleService.insertEntity(entity);
        return checkInsertResult(rows);
    }

    @PutMapping("/updateEntity")
    public ApiResponse updateEntity(@RequestBody @Validated RoleDTO entity) {
        int rows = sysRoleService.updateEntity(entity);
        return checkUpdateResult(rows);
    }

    @DeleteMapping("/deleteById")
    public ApiResponse deleteById(CheckBeforeOperateDTO checkBeforeOperateDTO) {
        // 验证操作者的密码是否正确
        checkCurrentLoginUserPassword(checkBeforeOperateDTO.getPassword());
        // 验证通过，继续操作
        int rows = sysRoleService.deleteById(checkBeforeOperateDTO.getId());
        return checkDeleteResult(rows);
    }

}
