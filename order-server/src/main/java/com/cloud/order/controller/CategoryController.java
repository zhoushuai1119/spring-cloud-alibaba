package com.cloud.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.order.domain.dto.BatchDelDTO;
import com.cloud.order.domain.entity.Category;
import com.cloud.order.service.CategoryService;
import com.cloud.platform.common.domain.request.PageQueryRequest;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.domain.response.PageQueryResponse;
import com.cloud.platform.web.aop.annotation.MethodLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:24
 * @version: V1.0
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 保存/更新档案
     * @param category
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @MethodLogger
    public BaseResponse enumCheck(@RequestBody @Valid Category category) {
        categoryService.saveCategory(category);
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 档案列表
     * @return
     */
    @PostMapping("/list")
    public BaseResponse<List<Category>> categosyList() {
        List<Category> categoryList = categoryService.categoryList();
        return BaseResponse.createSuccessResult(categoryList);
    }

    /**
     * 档案分页查询
     * @param pageQueryRequest
     * @return
     */
    @PostMapping("/page/list")
    public PageQueryResponse<Category> categosyList(@RequestBody PageQueryRequest pageQueryRequest) {
        Page<Category> pageList = categoryService.categoryPageList(pageQueryRequest);
        return PageQueryResponse.createSuccessResult(pageList.getRecords(),pageQueryRequest.getPageIndex(),
                pageQueryRequest.getPageSize(), pageList.getTotal());
    }

    /**
     * 更新档案数据
     * @param categoryId
     * @return
     */
    @PostMapping("/updateCategory/{categoryId}")
    public BaseResponse<String> updateCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.updateCategory(categoryId);
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 批量删除档案
     * @param categoryIdList
     * @return
     */
    @DeleteMapping("/batchDelCategory")
    public BaseResponse<String> delCategory(@RequestBody @Valid BatchDelDTO categoryIdList) {
        categoryService.batchDelCategory(categoryIdList);
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 获取档案详情
     * @param categoryId
     * @return
     */
    @GetMapping("/getCategory")
    public BaseResponse<Category> getCategory(@RequestParam(value = "categoryId") String categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return BaseResponse.createSuccessResult(category);
    }

    /**
     * 异步 Publisher事件
     * @param categoryId
     * @return
     */
    @PostMapping("/asyncPublisher/{categoryId}")
    public BaseResponse asyncPublisher(@PathVariable("categoryId") Integer categoryId) {
        categoryService.asyncSendMq(categoryId);
        return BaseResponse.createSuccessResult(null);
    }

}
