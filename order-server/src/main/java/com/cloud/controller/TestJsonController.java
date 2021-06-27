package com.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.annotation.ParamCheck;
import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.order.Category;
import com.cloud.common.entity.order.EnumTest;
import com.cloud.common.entity.order.dto.ParmsTestDto;
import com.cloud.common.enums.AgeEnum;
import com.cloud.common.service.order.CategoryService;
import com.cloud.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:24
 * @version: V1.0
 */
@RestController
@RefreshScope
public class TestJsonController {

    @Autowired
    private CategoryService categoryService;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/jsonTest")
    public EnumTest test45(){
        EnumTest enumTest = new EnumTest();
        enumTest.setName("周帅");
        enumTest.setAge(AgeEnum.AGEONE);
        enumTest.setCreateTime(new Date());

        String jsonSTr = JSONObject.toJSONString(enumTest);
        EnumTest jj = JSON.parseObject(jsonSTr, EnumTest.class);
        return jj;
    }

    @RequestMapping("/baidu/11")
    public String test(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String userId = request.getSession().getAttribute("userId").toString();
        return port+"****"+sessionId+"*****"+userId;
    }

    @RequestMapping("/baidu/22")
    @ParamCheck
    public Result<String> test22(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userId", "zhoushuaitest");
        return ResultUtil.getResult("1234667788");
    }

    @PostMapping("/baidu/55")
//    @ParamCheck
    public BaseResponse<List<Category>> test444(@RequestBody @Validated ParmsTestDto test) throws Exception {
        List<Category> categoryList = categoryService.categoryList();
        return BaseResponse.createSuccessResult(categoryList);
    }

    @RequestMapping("/categosyList")
    public Result<List<Category>> categosyList() {
        List<Category> categoryList = categoryService.categoryList();
        return ResultUtil.getResult(categoryList);
    }

    @RequestMapping("/dubboTest")
    public Result dubboTest() throws Exception {
        categoryService.updateTT();
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    @RequestMapping("/testAsync")
    public Result testAsync() {
        System.out.println("11111111111");
        categoryService.testAsync();
        System.out.println("22222222222222");
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

}
