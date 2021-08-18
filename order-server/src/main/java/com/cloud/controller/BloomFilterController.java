package com.cloud.controller;

import com.cloud.common.RedisBloomFilter;
import com.cloud.common.RedissionBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class BloomFilterController {

    @Value("${server.port}")
    private String port;
    @Autowired
    private RedisBloomFilter bloomFilter;
    @Autowired
    private RedissionBloomFilter filter;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("在线人员:"+
                session.getServletContext().getAttribute("onlinePersonNum"));
        return "hi " + name + " ,i am from port:" + port;
    }

    /**
     * 使用布隆过滤器 根据ID查询商品
     */
    @GetMapping("/bloomFilter/{id}")
    public String id(@PathVariable String id){
        //先查询布隆过滤器，过滤掉不可能存在的数据请求
        if (!bloomFilter.isExist(id)) {
            System.err.println("id:"+id+",布隆过滤...");
            return "非法请求";
        }
        //布隆过滤器认为可能存在，再走流程查询
        return "OK";
    }

    /**
     * 使用布隆过滤器 根据ID查询商品
     */
    @GetMapping("/redission/{id}")
    public String redission(@PathVariable String id){
        //先查询布隆过滤器，过滤掉不可能存在的数据请求
        if (!filter.isExist(id)) {
            System.err.println("id:"+id+",Redssion布隆过滤...");
            return "非法请求";
        }
        //布隆过滤器认为可能存在，再走流程查询
        return "OK";
    }

}
