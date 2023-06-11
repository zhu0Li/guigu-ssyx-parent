package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.product.mapper.SkuImageMapper;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-06-11
 */
@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService {

    @Override
    public List<SkuImage> findBySkuId(Long skuId) {
        List<SkuImage> skuImages = baseMapper.selectList(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));
        return skuImages;
    }
}
