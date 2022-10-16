package com.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class GatewayServerApplication {

    public static void main(String[] args) {
        //或者启动命令中加上 -Dcsp.sentinel.app.type=1
        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}

