package com.cloud.order.mapper.elasticsearch;

import com.cloud.order.domain.entity.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/24 19:03
 * @version: v1
 */
@Repository
public interface CategoryElasticRepository extends ElasticsearchRepository<Category, Long> {

}
