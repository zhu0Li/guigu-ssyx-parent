package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-06-12
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {

    @Autowired
    ActivityRuleMapper activityRuleMapper;

    @Autowired
    ActivitySkuMapper activitySkuMapper;

    @Autowired
    ActivityInfoMapper activityInfoMapper;

    @Autowired
    ProductFeignClient productFeignClient;

    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, null);
        //*分页查询对象获取列表数据
        List<ActivityInfo> activityInfos = activityInfoPage.getRecords();
        //*遍历集合，得到每个对象，向对象封装活动类型
        activityInfos.forEach(activityInfo -> {
            ActivityType activityType = activityInfo.getActivityType();
            activityInfo.setActivityTypeString(activityType.getComment());
        });
        return activityInfoPage;
    }

    /**
     * ! 根据活动id获取活动规则数据
     * @param id 活动id
     * @return 活动规则数据
     */
    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        Map<String, Object> map = new HashMap<>();
        //* 根据活动id，查询规则列表 activity_rule表
        List<ActivityRule> activityRuleList = activityRuleMapper.selectList(
                new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, id));
        map.put("activityRuleList",activityRuleList);

        //* 根据活动id，查询使用活动规则的sku  activity_sku表
        List<ActivitySku> activitySkus = activitySkuMapper.selectList(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        List<Long> skuIdList = activitySkus.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        // 远程调用
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        map.put("skuInfoList",skuInfoList);
        return map;
    }

    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {
        Long activityId = activityRuleVo.getActivityId();
        activityRuleMapper.delete(
                new QueryWrapper<ActivityRule>().eq("activity_id",activityId));
        activitySkuMapper.delete(
                new QueryWrapper<ActivitySku>().eq("activity_id", activityId));

        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
//        List<Long> couponIdList = activityRuleVo.getCouponIdList();

        ActivityInfo activityInfo = activityInfoMapper.selectById(activityId);
        for(ActivityRule activityRule : activityRuleList) {
            activityRule.setActivityId(activityId);
            activityRule.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(activityRule);
        }

        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        for(ActivitySku activitySku : activitySkuList) {
            activitySku.setActivityId(activityId);
            activitySkuMapper.insert(activitySku);
        }
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoListByKeyword(keyword);
        if (skuInfoList.isEmpty()){
            return null;
        }

        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getId).collect(Collectors.toList());

        //* 判断添加商品前是否参加过活动，日过参加过，且活动正在进行中，则排除商品
        //* 查询两张表 activity_info activity_sku，编写sql语句
        List<Long> existSkuIdList = baseMapper.selectSkuIdListExist(skuIdList);

        //* 排除已经参加活动的商品
        List<SkuInfo> findSkuList = new ArrayList<>();
        for(SkuInfo skuInfo : skuInfoList) {
            if(!existSkuIdList.contains(skuInfo.getId())) {
                findSkuList.add(skuInfo);
            }
        }
        return findSkuList;
    }
}
