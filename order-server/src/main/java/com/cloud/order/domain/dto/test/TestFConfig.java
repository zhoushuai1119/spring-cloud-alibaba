package com.cloud.order.domain.dto.test;

import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/8 21:24
 * @version: v1
 */
public class TestFConfig {

    @Bean
    public TestG testG(){
        return new TestG();
    }

}
