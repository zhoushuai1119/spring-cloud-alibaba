package com.cloud.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/17 15:34
 * @version: v1
 */
@Slf4j
public class CarFactoryBean implements FactoryBean<Car> {

    /**
     * 测试属性
     */
    private Integer test;

    @Override
    public Car getObject() {
        log.info("调用 FactoryBean getObject 方法");
        return new Car();
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }


    @Override
    public boolean isSingleton() {
        return true;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

}
