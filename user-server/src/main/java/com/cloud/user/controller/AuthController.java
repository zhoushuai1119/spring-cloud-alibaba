package com.cloud.user.controller;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.user.config.WebRequestConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 注解实现权限测试
 * 需要注意的一点，一般在Service层中我们会天剑@Transactioncal注解来添加事务，这个时候Service层呢就已经是一个代理对象了，
 * 这个时候将权限的注解加到Service层是不好用的， 这个时候需要加到Controller层中，
 * 也就是不能让Service层是代理的代理，这个时候在注入的时候会产生类型转换异常
 * 原文链接：https://blog.csdn.net/changudeng1992/article/details/81914572
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
     *
     * @return
     */
    @PostMapping("role")
    @RequiresRoles(value = {"admin", "manager"}, logical = Logical.AND)
    public BaseResponse authRoleTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有角色权限访问");
    }

    /**
     * 操作权限测试
     *
     * @return
     */
    @PostMapping("permiss")
    @RequiresPermissions(value = {"query", "del"}, logical = Logical.AND)
    public BaseResponse authPermissTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有操作权限访问");
    }

    /**
     * 登录权限测试(已登录才有权访问)
     *
     * @return
     */
    @PostMapping("login")
    @RequiresAuthentication
    public BaseResponse authLoginTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有登录权限访问");
    }


    /**
     * 当前Subject已经身份验证或者通过记住我登录我都可以访问
     * 注: 未登录但是记住我时也有权限访问
     *
     * @return
     */
    @PostMapping("rememberMe")
    @RequiresUser
    public BaseResponse authRememberMeTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("有记住我权限访问");
    }

    /**
     * 当前Subject没有身份验证或者通过记住我登录我都可以访问
     * 即游客权限
     *
     * @return
     */
    @PostMapping("guest")
    @RequiresGuest
    public BaseResponse authGuestTest() {
        log.info("当前登录用户:{}", JsonUtil.toString(webRequestConfig.getUserInfo()));
        return BaseResponse.createSuccessResult("游客权限访问");
    }

    @GetMapping("kickout")
    public BaseResponse kickoutTest() {
        return BaseResponse.createSuccessResult("异地登录");
    }

}
