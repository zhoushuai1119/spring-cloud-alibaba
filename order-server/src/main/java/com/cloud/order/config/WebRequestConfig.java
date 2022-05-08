package com.cloud.order.config;

import com.cloud.order.common.constants.OrderConstant;
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

    public String getUserName() {
        return Optional.ofNullable(request.getHeader(OrderConstant.CurrentLogin.USER_NAME))
                .orElse(OrderConstant.CurrentLogin.DEFAULT_USER_NAME);
    }

}
