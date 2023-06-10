package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-06-10
 */
@Api(tags = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    //!开通区域列表
    @ApiOperation("开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       RegionWareQueryVo regionWareQueryVo){

        Page<RegionWare> pageParam = new Page<>(page, limit);

        IPage<RegionWare> pageModel = regionWareService.selectPageRegionWare(pageParam, regionWareQueryVo);

        return Result.ok(pageModel);
    }

    //! 添加开通区域
    @ApiOperation("添加开通区域")
    @PostMapping("save")
    public Result addRegionWare(@RequestBody RegionWare regionWare){
        boolean isSucess = regionWareService.saveRegionWare(regionWare);
        if (isSucess){
            return Result.ok(null);
        }else {
            throw new SsyxException("添加开通区域失败",201);
        }
    }

    //!删除开通区域
    @ApiOperation(value = "删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status) {
        regionWareService.updateStatus(id, status);
        return Result.ok(null);
    }

}

