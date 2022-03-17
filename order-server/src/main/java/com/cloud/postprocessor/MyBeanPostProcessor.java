package com.cloud.postprocessor;

import com.cloud.common.entity.order.BeanTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/16 15:14
 * @version: v1
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, PriorityOrdered, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 在任何bean初始化回调之前;属性赋值之后（例如InitializingBean的afterPropertiesSet或自定义init-method）之前，
     * 将此BeanPostProcessor应用于给定的新bean实例。
     * 返回的Bean实例可能是原始实例的包装。
     * 默认实现按原样返回给定的 bean。
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName,"beanTest")) {
            log.info("========开始执行BeanPostProcessor, 准备开始BeanTest初始化==================");
            log.info("步骤【5】: 调用BeanPostProcessor 接口方法 postProcessBeforeInitialization 对属性进行更改！");
            BeanTest beanTest = (BeanTest) applicationContext.getBean(beanName);
            beanTest.setName("zhangsan");
            beanTest.setAge(30);
        }
        return bean;
    }

    /**
     * 在任何bean初始化回调之后（例如InitializingBean的afterPropertiesSet或自定义init-method）之后，
     * 将此BeanPostProcessor应用于给定的新bean实例。
     * 如果是FactoryBean，则将为FactoryBean实例和由FactoryBean创建的对象（从Spring 2.0开始）调用此回调。
     * 可以通过FactoryBean检查相应bean实例来决定是应用到FactoryBean还是FactoryBean创建的对象，还是两者都应用。
     * 返回的Bean实例可能是原始实例的包装。
     * 默认实现按原样返回给定的 bean。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName,"beanTest")) {
            log.info("步骤【9】: 调用BeanPostProcessor 接口方法 postProcessAfterInitialization");
            log.info("========执行BeanPostProcessor结束，BeanTest 初始化结束==================");
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * BeanPostProcessor有很多个，而且每个BeanPostProcessor都影响多个Bean，其执行顺序至关重要，必须能够控制其执行顺序才行。
     * 关于执行顺序这里需要引入两个排序相关的接口：PriorityOrdered、Ordered
     * PriorityOrdered是一等公民，首先被执行，PriorityOrdered公民之间通过接口返回值排序
     * Ordered是二等公民，然后执行，Ordered公民之间通过接口返回值排序
     * 都没有实现是三等公民，最后执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
