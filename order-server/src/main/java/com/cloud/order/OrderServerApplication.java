package com.cloud.order;

import com.cloud.openfeign.log.config.FeignLogAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//自动装配的方法在 AutoConfigurationImportSelector.getAutoConfigurationEntry
//而不是 AutoConfigurationImportSelector.selectImports
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = {FeignLogAutoConfiguration.class})
//启用@WebServlet、@WebFilter和@WebListener注释的类的自动注册
@ServletComponentScan
@EnableAsync
@ComponentScan("com.cloud")
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}

