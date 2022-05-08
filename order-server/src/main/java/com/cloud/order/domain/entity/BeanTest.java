package com.cloud.order.domain.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/16 11:41
 * @version: v1
 */
@Data
@Slf4j
public class BeanTest implements InitializingBean, DisposableBean, ApplicationContextAware, BeanNameAware, BeanFactoryAware {

    private Integer id;

    private String name;

    private Integer age;

    private String beanName;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public BeanTest() {
        log.info("步骤【1】: 执行beanTest constructor。。。。。");
    }

    public BeanTest(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor > @PostConstruct > InitializingBean > init-method
     */
    @PostConstruct
    public void postConstruct() {
        log.info("步骤【6】: 执行@PostConstruct PostConstruct ....");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("步骤【7】: 调用InitializingBean afterPropertiesSet method.......");
    }

    public void init() {
        log.info("步骤【8】: 执行BeanTest init method.......");
        BeanTest beanTest = (BeanTest) applicationContext.getBean("beanTest");
        log.info("init method 获取到的beanTest的name:{}",beanTest.getName());
    }

    @PreDestroy
    public void preDestroy() {
        log.info("==========开始准备销毁BeanTest==========");
        log.info("步骤【10】: 调用 @PreDestroy preDestroy method.......");
    }

    @Override
    public void destroy() {
        log.info("步骤【11】: 调用DisposableBean destroy method.......");
    }

    public void destory() {
        log.info("步骤【12】: 执行BeanTest destory method.......");
        log.info("==========销毁BeanTest结束==========");
    }

    @Override
    public void setBeanName(String beanName) {
        log.info("================开始属性赋值=============");
        System.out.println();
        log.info("================属性赋值结束=============");
        log.info("================开始调用Aware方法=============");
        log.info("步骤【2】: 调用BeanNameAware setBeanName method.......");
        this.beanName = beanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("步骤【3】: 调用 BeanFactoryAware setBeanFactory method.......");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("步骤【4】: 调用ApplicationContextAware setApplicationContext method.......");
        log.info("================调用Aware方法结束=============");
        this.applicationContext = applicationContext;
    }

}
