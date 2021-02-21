package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ActivitiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiServerApplication.class, args);
    }

}

