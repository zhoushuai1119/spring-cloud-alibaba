package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.payment.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
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

    @PostMapping("/send")
    public BaseResponse sendMessage(String content){
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/saveUser")
    public BaseResponse<String> saveUser() throws Exception {
        userService.saveUser(new User());
        return BaseResponse.createSuccessResult(null);
    }

    @RequestMapping("/categosyList")
    public String categosyList(){
        System.out.println("tttttttttttttttttttt");
        return "i'm hdhdhdhdhdh";
    }


}
