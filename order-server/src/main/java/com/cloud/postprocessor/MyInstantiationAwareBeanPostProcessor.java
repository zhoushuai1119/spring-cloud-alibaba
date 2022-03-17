package com.cloud.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/16 16:53
 * @version: v1
 */
@Slf4j
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * 在bean实例化之前执行
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (Objects.equals(beanName,"beanTest")) {
            log.info("==========开始准备BeanTest实例化===============");
            log.info("调用 InstantiationAwareBeanPostProcessor  postProcessBeforeInstantiation方法 ");
        }
        return null;
    }


    /**
     * 在bean实例化之后执行
     * @param bean
     * @param beanName
     * @return 如果返回false则不进行属性赋值
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName,"beanTest")) {
            log.info("调用 InstantiationAwareBeanPostProcessor  postProcessAfterInstantiation方法 ");
            log.info("==========BeanTest实例化结束===============");
        }
        return true;
    }

}
