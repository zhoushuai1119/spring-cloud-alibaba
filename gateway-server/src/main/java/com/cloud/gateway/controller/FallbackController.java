package com.cloud.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 默认降级处理
 * @author: 周帅
 * @date: 2021/2/3 13:47
 * @version: V1.0
 */
@RestController
public class FallbackController {

    @RequestMapping("fallback")
    public Map<String,String> fallback(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>();
        map.put("resultCode","fail");
        map.put("resultMessage","服务异常");
        map.put("resultObj","null");
        return map;
    }

}
