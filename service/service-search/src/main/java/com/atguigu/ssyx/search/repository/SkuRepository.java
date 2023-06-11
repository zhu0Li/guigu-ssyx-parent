package com.atguigu.ssyx.search.repository;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ：zhuo
 * @description：TODO
 * @date ：2023/6/11 15:20
 */
public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {
}
