package com.cloud.config;

import com.cloud.common.entity.order.BeanTest;
import com.cloud.common.entity.order.CarFactoryBean;
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


    /**
     * 当配置文件中<bean>的class属性配置的实现类是FactoryBean时，
     * 通过getBean()方法返回的不是FactoryBean本身，
     * 而是FactoryBean#getObject()方法所返回的对象，
     * 相当于FactoryBean#getObject()代理了getBean()方法。
     * @return
     */
    @Bean
    public CarFactoryBean carFactoryBean(){
        return new CarFactoryBean();
    }

}
