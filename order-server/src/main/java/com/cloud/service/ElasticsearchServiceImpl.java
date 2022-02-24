package com.cloud.service;

import com.cloud.common.entity.order.Category;
import com.cloud.dao.elasticsearch.CategoryElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 14:38
 * @version: v1
 */
@Service
public class ElasticsearchServiceImpl {

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    @Autowired
    private CategoryElasticRepository categoryElasticRepository;

    public void saveCategory(Category category) {
        categoryElasticRepository.save(category);
    }

    public Category getCategoryById(String categoryId) {
        Category category = esRestTemplate.get(categoryId, Category.class);
        return category;
    }

}
