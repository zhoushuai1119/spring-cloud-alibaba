package com.cloud.config;

import com.cloud.filters.KickoutSessionControlFilter;
import com.cloud.filters.MyFormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:zhoushuai
 * @Description: 我们希望Shiro来管理我们的自定义Filter，
 * 所以我们要想办法取消SpringBoot自动注册我们的Filter
 */
@Configuration
public class ShiroFilterRegisterConfig {

    @Bean
    public FilterRegistrationBean myFormRegistration(MyFormAuthenticationFilter myFormFilter){
        FilterRegistrationBean registration = new FilterRegistrationBean(myFormFilter);
        //取消Filter自动注册,不会添加到FilterChain中
        registration.setEnabled(false);
        return  registration;
    }

    @Bean
    public FilterRegistrationBean kickoutRegistration(KickoutSessionControlFilter kickoutFilter){
        FilterRegistrationBean registration = new FilterRegistrationBean(kickoutFilter);
        registration.setEnabled(false);
        return  registration;
    }
}
