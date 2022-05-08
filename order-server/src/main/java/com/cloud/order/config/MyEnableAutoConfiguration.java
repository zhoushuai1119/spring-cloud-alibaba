package com.cloud.order.config;

import com.cloud.order.domain.dto.test.TestE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 15:33
 * @version: v1
 */
@Configuration
public class MyEnableAutoConfiguration {

    @Bean
    public TestE testE() {
        return new TestE();
    }

}
