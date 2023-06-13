package com.atguigu.ssyx.client.product;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/11 15:31
 */
@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

    @PostMapping("/api/product/inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList);

    @ApiOperation(value = "根据关键字查询sku列表")
    @PostMapping("/api/product/inner/findSkuInfoListByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoListByKeyword(@PathVariable String keyword);

    @ApiOperation(value = "根据分类idList获取分类List")
    @PostMapping("/api/product/inner/findCategoryList/{idList}")
    public List<Category> findCategoryList(@PathVariable List<Long> idList);
}
