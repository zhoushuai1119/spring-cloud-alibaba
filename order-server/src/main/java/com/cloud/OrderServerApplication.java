package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//启用@WebServlet、@WebFilter和@WebListener注释的类的自动注册
@ServletComponentScan
@EnableAsync
//@RefreshScope加在启动类不会实时刷新配置，必须重启服务。
//在动态配置的地方添加@RefreshScope才可以动态获取配置信息
@RefreshScope
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}

