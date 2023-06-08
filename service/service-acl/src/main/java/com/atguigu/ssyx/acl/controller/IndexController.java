package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zhuo
 * @description：用户登录接口
 * @date ：2023/6/8 23:11
 */
@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin  //跨域
public class IndexController {

    //! login 登录
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(){
        //*返回token值
        Map<String, String> map = new HashMap<>();
        map.put("token", "token-admin");
        return Result.ok(map);
    }

    //! getInfo 获取信息
    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info(){
        Map<String, String> map = new HashMap<>();
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //! logout 退出
    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logou(){
        return Result.ok(null);
    }
}
