package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 去掉Spring-Security安全验证，整合Shiro实现权限认证
 * 注意: 该方法对7.1.0.M6版本无效；因为Activiti的Springboot配置类中强引用了
 * userDetailsService,亲测使用7.1.0.M4及以下版本有效
 *
 * 7.1.0.M4 流程部署报错，改成7.1.0.M2即可解决
 * Unknown column 'VERSION_' in 'field list'
 */
@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class
        }
)
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@RefreshScope
public class ActivitiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiServerApplication.class, args);
    }

}

