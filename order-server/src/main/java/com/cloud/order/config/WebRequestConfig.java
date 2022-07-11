package com.cloud.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
    private HttpServletRequest httpServletRequest;


    /**
     * 获取请求头token
     * @return
     */
    public String getLoginToken(){
        return httpServletRequest.getHeader("login-token");
    }

}
