package com.cloud.controller;

import com.cloud.entity.UserTest;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserTestController {

    @Autowired
    private UserService userService;


    @PostMapping("/saveUser")
    public BaseResponse<String> saveUser() throws Exception {
        userService.saveUser(new UserTest());
        return BaseResponse.createSuccessResult(null);
    }

}
