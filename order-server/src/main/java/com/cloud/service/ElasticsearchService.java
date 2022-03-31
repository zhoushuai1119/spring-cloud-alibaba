package com.cloud.service;

import com.cloud.dao.elasticsearch.CategoryElasticRepository;
import com.cloud.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 14:38
 * @version: v1
 */
@Service
public class ElasticsearchService {

    @Autowired
    private CategoryElasticRepository categoryElasticRepository;


    public void saveCategory(Category category) {
        categoryElasticRepository.save(category);
    }


    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = categoryElasticRepository.findById(categoryId);
        if (category.isPresent()) {
            return category.get();
        }
        return null;
    }

}
