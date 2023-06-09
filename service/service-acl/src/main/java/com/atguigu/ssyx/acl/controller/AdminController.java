package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：zhuo
 * @description：用户管理接口
 * @date ：2023/6/9 19:48
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    //! 用户列表
    @ApiOperation("用户列表")
    @GetMapping("{current}/{limit}")
    public Result page(@PathVariable Long current, @PathVariable Long limit,
                       AdminQueryVo adminQueryVo){
        Page<Admin> pageParam = new Page<>(current,limit);

        IPage<Admin> pageModel = adminService.selectPageUser(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    //! 添加用户
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin){
        boolean isSuccess = adminService.add(admin);
        if (isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //! 修改用户
    @ApiOperation("修改用户")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin){
        boolean isSuccess = adminService.changeById(admin);
        if (isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //! 根据id查询用户
    @ApiOperation("根据id查询用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Admin byId = adminService.getById(id);
        return Result.ok(byId);
    }

    //! 根据id删除用户
    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id){
        boolean isSuccess = adminService.removeById(id);
        if (isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //! 批量删除用户
    @ApiOperation("批量删除用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean isSuccess = adminService.removeByIds(ids);
        if (isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

    //! 获取所有角色，以及根据用户id查询其所有角色
    @ApiOperation("获取用户所有角色")
    @GetMapping("toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId){
        //*返回的map集合中包含两部分数据：所有角色 和 为用户分配的角色
        Map<String, Object> map = roleService.getRoleByAdminId(adminId);
        return Result.ok(map);
    }

    //! 为用户分配角色
    @ApiOperation("为用户分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long adminId,
                           @RequestParam Long[] roleId){
        roleService.saveAdminRole(adminId,roleId);
        return Result.ok(null);
    }
}
