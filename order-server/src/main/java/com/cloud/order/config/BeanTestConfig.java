package com.cloud.order.config;

import com.cloud.order.domain.entity.BeanTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/16 11:41
 * @version: v1
 */
@Configuration
public class BeanTestConfig {

    /**
     * 方法名将默认成该bean定义的id
     * @return
     */
    @Bean(initMethod = "init",destroyMethod = "destory")
    public BeanTest beanTest(){
        BeanTest beanTest = new BeanTest();
        beanTest.setName("zhoushuai");
        return beanTest;
    }

}
