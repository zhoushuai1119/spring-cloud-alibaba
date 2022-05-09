package com.cloud.product.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: Feign配置注册（全局）
 * @author: zhou shuai
 * @date: 2022/1/20 16:56
 * @version: v1
 */
@Configuration
@Slf4j
public class FeignSupportConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 解决seata的xid未传递
        String xid = RootContext.getXID();
        log.info("feign调用时seata全局事务ID:{}",xid);
        //可以设置请求头数据
        requestTemplate.header(RootContext.KEY_XID, xid);
    }

    /**
     * 打印feign请求日志
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
