package com.cloud.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.utils.RedisUtil;
import com.cloud.order.config.WebRequestConfig;
import com.cloud.order.domain.dto.BatchDelDTO;
import com.cloud.order.domain.dto.ParmsTestDTO;
import com.cloud.order.domain.entity.Category;
import com.cloud.order.enums.CategoryTypeEnum;
import com.cloud.order.service.CategoryService;
import com.cloud.order.service.SqlService;
import com.cloud.order.utils.ThreadLocalUtil;
import com.cloud.platform.common.domain.request.PageQueryRequest;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.domain.response.PageQueryResponse;
import com.cloud.platform.web.aop.annotation.MethodLogger;
import com.cloud.platform.web.validate.Save;
import com.cloud.platform.web.validate.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private WebRequestConfig webRequestConfig;


    /**
     * 参数校验测试
     *
     * @param parmsTestDto
     * @return
     */
    @PostMapping("/param/check")
    @MethodLogger
    public BaseResponse<ParmsTestDTO> paramCheck(@RequestBody @Validated({Save.class, Update.class}) ParmsTestDTO parmsTestDto) {
        parmsTestDto.setStr(Thread.currentThread().getName());
        ThreadLocalUtil.set(parmsTestDto);
        parmsTestDto.setLocalDateTime(LocalDateTime.now());
        parmsTestDto.setCurrentUserName(webRequestConfig.getUserName());
        //categoryService.categoryList();
        redisUtil.set("parmsTestDto", parmsTestDto, 60 * 10, TimeUnit.SECONDS);
        //log.info("redisParms:{}", redisParms);
        ParmsTestDTO parmsRedis = (ParmsTestDTO) redisUtil.get("parmsTestDto");
        return BaseResponse.createSuccessResult(parmsRedis);
    }

    @PostMapping("/enum/test")
    public BaseResponse enumTest(CategoryTypeEnum categoryType) {
        return BaseResponse.createSuccessResult(categoryType);
    }


    @PostMapping("/saveOrUpdate")
    @MethodLogger
    public BaseResponse enumCheck(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/list")
    public BaseResponse<List<Category>> categosyList() {
        List<Category> categoryList = categoryService.categoryList();
        return BaseResponse.createSuccessResult(categoryList);
    }

    @PostMapping("/page/list")
    public PageQueryResponse<Category> categosyList(@RequestBody PageQueryRequest pageQueryRequest) {
        Page<Category> pageList = categoryService.categoryPageList(pageQueryRequest);
        return PageQueryResponse.createSuccessResult(pageList.getRecords(),pageQueryRequest.getPageIndex(),
                pageQueryRequest.getPageSize(), pageList.getTotal());
    }

    @PostMapping("/updateCategory/{categoryId}")
    public BaseResponse<String> updateCategory(@PathVariable("categoryId") String categoryId) {
        categoryService.updateCategory(categoryId);
        return BaseResponse.createSuccessResult(null);
    }

    @DeleteMapping("/batchDelCategory")
    public BaseResponse<String> delCategory(@RequestBody @Valid BatchDelDTO categoryIdList) {
        categoryService.batchDelCategory(categoryIdList);
        return BaseResponse.createSuccessResult(null);
    }

    @GetMapping("/getCategory")
    public BaseResponse<Category> getCategory(@RequestParam(value = "categoryId") String categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return BaseResponse.createSuccessResult(category);
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
