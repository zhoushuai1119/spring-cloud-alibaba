package com.cloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dto.ParmsTestDto;
import com.cloud.entity.Category;
import com.cloud.enums.CategoryTypeEnum;
import com.cloud.platform.common.request.PageQueryRequest;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.platform.common.response.PageQueryResponse;
import com.cloud.platform.web.aop.annotation.MethodLogger;
import com.cloud.platform.web.validate.Save;
import com.cloud.platform.web.validate.Update;
import com.cloud.service.CategoryService;
import com.cloud.service.SqlService;
import com.cloud.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:24
 * @version: V1.0
 */
@RestController
@RequestMapping("/category")
@Api(value = "test接口", tags = "test接口")
@Slf4j
//需要实时刷新配置，需要在controller添加@RefreshScope
@RefreshScope
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SqlService sqlService;


    /**
     {
     "str":"11",
     "email":"126151551@qq.com",
     "mobile":"17756228264",
     "length":6666,
     "max":6,
     "groupTest":11,
     "orderEnum":1
     }
     *
     * @param parmsTestDto
     * @return
     */
    @PostMapping("/param/check")
    @MethodLogger
    public BaseResponse<ParmsTestDto> paramCheck(@RequestBody @Validated({Save.class, Update.class}) ParmsTestDto parmsTestDto) {
        parmsTestDto.setStr(Thread.currentThread().getName());
        ThreadLocalUtil.set(parmsTestDto);
        parmsTestDto.setTime(LocalDateTime.now());
        categoryService.categoryList();
        return BaseResponse.createSuccessResult(parmsTestDto);
    }

    @PostMapping("/enum/test")
    //加@RequestBody会报错
    public BaseResponse enumTest(CategoryTypeEnum categoryType) {
        return BaseResponse.createSuccessResult(categoryType);
    }


    @PostMapping("/save/category")
    @MethodLogger
    public BaseResponse enumCheck(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/category/list")
    public BaseResponse<List<Category>> categosyList() {
        List<Category> categoryList = categoryService.categoryList();
        return BaseResponse.createSuccessResult(categoryList);
    }

    @PostMapping("/category/page/list")
    public PageQueryResponse<Category> categosyList(@RequestBody PageQueryRequest pageQueryRequest) {
        Page<Category> pageList = categoryService.categoryPageList(pageQueryRequest);
        return PageQueryResponse.createSuccessResult(pageList.getRecords(), pageQueryRequest.getPageIndex(),
                pageList.getSize(), pageQueryRequest.getPageSize());
    }

    @PostMapping("/updateCategory")
    public BaseResponse<String> updateCategory() {
        categoryService.updateCategory();
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/asyncSendMq/{categoryId}")
    public BaseResponse asyncSendMq(@PathVariable("categoryId") Integer categoryId) {
        categoryService.asyncSendMq(categoryId);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/sql/reNameTable")
    public BaseResponse reNameTable() throws SQLException {
        sqlService.reNameTable(null, null);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/sql/copyNameTable")
    public BaseResponse copyNameTable() throws SQLException {
        sqlService.copyNameTable(null, null);
        return BaseResponse.createSuccessResult(null);
    }

}
