package com.cloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.utils.RedisUtil;
import com.cloud.config.WebRequestConfig;
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
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SqlService sqlService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Mapper mapper;

    @Autowired
    private WebRequestConfig webRequestConfig;


    /**
     * 参数校验测试
     *
     * @param parmsTestDto
     * @return
     */
    @PostMapping("/param/check")
    @MethodLogger
    public BaseResponse<List<ParmsTestDto>> paramCheck(@RequestBody @Validated({Save.class, Update.class}) ParmsTestDto parmsTestDto) {
        parmsTestDto.setStr(Thread.currentThread().getName());
        ThreadLocalUtil.set(parmsTestDto);
        parmsTestDto.setLocalDateTime(LocalDateTime.now());
        parmsTestDto.setCurrentUserName(webRequestConfig.getUserName());
        //categoryService.categoryList();
        redisUtil.set("parmsTestDto", Lists.newArrayList(parmsTestDto), 60 * 10);
        //log.info("redisParms:{}", redisParms);
        List<ParmsTestDto> parmsTestDtoList = (List<ParmsTestDto>) redisUtil.get("parmsTestDto");
        return BaseResponse.createSuccessResult(parmsTestDtoList);
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
        return BaseResponse.createSuccessResult(category);
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

    @PostMapping("/delCategory")
    public BaseResponse<String> delCategory() {
        categoryService.delCategory();
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
