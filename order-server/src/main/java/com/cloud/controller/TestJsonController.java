package com.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.entity.order.EnumTest;
import com.cloud.common.enums.AgeEnum;
import com.cloud.common.service.order.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        if (true) {
            throw new Exception("测试异常");
        }
        return port+"****"+sessionId;
    }

    @RequestMapping("/baidu/22")
    public String test22(){
        return "baiduddd222222jj";
    }

    @RequestMapping("/categosyList")
    public void categosyList() throws Exception {
        categoryService.updateTT();
    }

}
