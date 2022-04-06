package com.cloud.config;

import com.cloud.constants.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @description: 工具类
 * @author: zhoushuai
 * @date: 2018/6/19 23:08
 * @version: V1.0.0
 */
@Slf4j
@Component
public class WebRequestConfig {

    @Resource
    private HttpServletRequest request;

    public String getToken() {
        return Optional.ofNullable(request.getHeader(UserConstant.Token.TOEKN)).orElse(null);
    }

}
