package com.cloud.user.config;

import com.cloud.user.domain.entity.User;
import com.cloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description: 工具类
 * @author: zhoushuai
 * @date: 2018/6/19 23:08
 * @version: V1.0.0
 */
@Slf4j
@Component
public class WebRequestConfig {

    @Autowired
    private UserService userService;


    /**
     * 获取当前登录用户
     * @return
     */
    public User getUserInfo(){
        Object username = SecurityUtils.getSubject().getPrincipal();
        if (Objects.nonNull(username)) {
            return userService.findUserByName(String.valueOf(username));
        }
        return null;
    }

}
