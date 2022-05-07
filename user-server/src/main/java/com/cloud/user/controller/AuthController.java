package com.cloud.user.controller;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.user.config.WebRequestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 权限测试
 * @author: zhou shuai
 * @date: 2022/5/7 23:58
 * @version: v1
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private WebRequestConfig webRequestConfig;

    /**
     * 角色权限测试
     * @return
     */
    @PostMapping("role")
    public BaseResponse authRoleTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有角色权限访问");
    }

    /**
     * 操作权限测试
     * @return
     */
    @PostMapping("permiss")
    public BaseResponse authPermissTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有操作权限访问");
    }

    /**
     * 登录权限测试(已登录才有权访问)
     * @return
     */
    @PostMapping("login")
    public BaseResponse authLoginTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有登录权限访问");
    }


    /**
     * 记住我权限测试(已登录或者记住我时有权限访问)
     * 注: 未登录但是记住我时也有权限访问
     * @return
     */
    @PostMapping("rememberMe")
    public BaseResponse authRememberMeTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有记住我权限访问");
    }

}
