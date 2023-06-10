package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/10 14:40
 */
@Api(tags = "菜单管理")
@RequestMapping("/admin/acl/permission")
@RestController
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //! 查询所有菜单
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list(){
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //! 添加菜单
    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission){
        boolean isSuccess = permissionService.save(permission);
        if (isSuccess){
            return Result.ok(null);
        }else {
            throw new SsyxException("添加菜单失败", 201);
        }
    }

    //! 修改菜单
    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result update(@RequestBody Permission permission){
        boolean isSuccess = permissionService.updateById(permission);
        if (isSuccess){
            return Result.ok(null);
        }else {
            throw new SsyxException("修改菜单失败", 201);
        }
    }

    //! 递归删除菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean isSuccess = permissionService.deleteById(id);
        if (isSuccess){
            return Result.ok(null);
        }else {
            throw new SsyxException("菜单删除失败", 201);
        }
    }

    //!
    //!
}
