package com.cloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.aop.annotation.MethodLogger;
import com.cloud.common.aop.annotation.NewAuthV2;
import com.cloud.common.beans.request.PageQueryRequest;
import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.beans.response.PageQueryResponse;
import com.cloud.common.entity.order.Category;
import com.cloud.common.entity.order.dto.ParmsTestDto;
import com.cloud.common.service.order.CategoryService;
import com.cloud.common.service.order.SqlService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
//需要实时刷新配置，需要在controller添加@RefreshScope
@RefreshScope
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SqlService sqlService;

    @Value("${test.parms}")
    private String testParms;


    @GetMapping("/config/testParms")
    public BaseResponse<String> testParms() {
        return BaseResponse.createSuccessResult(testParms);
    }


    @PostMapping("/param/check")
    @MethodLogger
    public BaseResponse<String> paramCheck(@RequestBody @Validated ParmsTestDto parmsTestDto) {
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/category/list")
    @NewAuthV2
    public BaseResponse<List<Category>> categosyList() {
        List<Category> categoryList = categoryService.categoryList();
        return BaseResponse.createSuccessResult(categoryList);
    }

    @PostMapping("/category/page/list")
    public PageQueryResponse<Category> categosyList(@RequestBody PageQueryRequest pageQueryRequest) {
        Page<Category> pageList = categoryService.categoryPageList(pageQueryRequest);
        return PageQueryResponse.createSuccessPageResult(pageList);
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
        sqlService.reNameTable(null,null);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/sql/copyNameTable")
    public BaseResponse copyNameTable() throws SQLException {
        sqlService.copyNameTable(null,null);
        return BaseResponse.createSuccessResult(null);
    }

}
