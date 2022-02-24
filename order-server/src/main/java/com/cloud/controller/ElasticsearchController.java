package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.order.Category;
import com.cloud.common.service.order.CategoryService;
import com.cloud.service.ElasticsearchServiceImpl;
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
    private ElasticsearchServiceImpl categoryElasticsearchService;

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
