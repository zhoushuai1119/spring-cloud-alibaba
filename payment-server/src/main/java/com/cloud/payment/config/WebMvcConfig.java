package com.cloud.payment.config;

import com.cloud.payment.interceptor.LoginInterceptor;
import com.cloud.platform.web.factory.StringToEnumConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

/**
 * @Author： Zhou Shuai
 * @Date： 9:08 2018/12/19
 * @Description：
 * @Version:  01
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        //配置拦截的url
        ir.addPathPatterns("/**");
        //配置不拦截的路径
        ir.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui/**");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

}
