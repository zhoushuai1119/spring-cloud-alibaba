package com.cloud.controller;

import com.cloud.entity.Category;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.service.CategoryService;
import com.cloud.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 14:13
 * @version: v1
 */
@RestController
public class ElasticsearchController {

    @Resource
    private ElasticsearchService categoryElasticsearchService;

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/saveCategoty")
    public BaseResponse saveCategory(Long categoryId) {
        Category category = categoryService.getById(categoryId);
        categoryElasticsearchService.saveCategory(category);
        return BaseResponse.createSuccessResult(null);
    }


    @GetMapping("/getCategoty")
    public BaseResponse<Category> getCategoty(Long categoryId) {
        Category category = categoryElasticsearchService.getCategoryById(categoryId);
        return BaseResponse.createSuccessResult(category);
    }

}
