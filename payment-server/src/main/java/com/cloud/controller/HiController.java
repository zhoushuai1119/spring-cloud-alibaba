package com.cloud.controller;

import com.cloud.common.entity.payment.User;
import com.cloud.common.service.payment.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HiController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    public String callHome(){
        return restTemplate.getForObject("http://localhost:8989/miya", String.class);
    }

    @RequestMapping("/info")
    public String info(){
        return "i'm service-hi";
    }

    @RequestMapping("/saveUser")
    public String saveUser() throws Exception {
        userService.saveUser(new User());
        return "i'm zhoushuai";
    }

    @RequestMapping("/categosyList")
    public String categosyList(){
        System.out.println("tttttttttttttttttttt");
        return "i'm hdhdhdhdhdh";
    }


}
