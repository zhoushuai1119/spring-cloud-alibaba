package com.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.annotation.ParamCheck;
import com.cloud.common.beans.Result;
import com.cloud.common.entity.order.EnumTest;
import com.cloud.common.entity.order.dto.ParmsTestDto;
import com.cloud.common.enums.AgeEnum;
import com.cloud.common.service.order.CategoryService;
import com.cloud.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;

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
    public String test(HttpServletRequest request) throws Exception {
        String sessionId = request.getSession().getId();
        /*if (true) {
            throw new Exception("测试异常");
        }*/
        return port+"****"+sessionId;
    }

    @RequestMapping("/baidu/22")
    @ParamCheck
    public Result<String> test22(@NotBlank(message = "aa不能为空") String aa, String bb){
        return ResultUtil.getResult("1234667788");
    }

    @RequestMapping("/baidu/55")
    @ParamCheck
    public Result<String> test444(@Valid ParmsTestDto test, BindingResult result){
        return ResultUtil.getResult("1234");
    }

    @RequestMapping("/categosyList")
    public void categosyList() throws Exception {
        categoryService.updateTT();
    }

}
