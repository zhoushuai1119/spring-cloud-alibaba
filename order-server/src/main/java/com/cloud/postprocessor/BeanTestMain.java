package com.cloud.postprocessor;

import com.cloud.common.entity.order.BeanTest;
import com.cloud.config.BeanTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Constructor;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/16 11:44
 * @version: v1
 */
@Slf4j
public class BeanTestMain {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanTestConfig.class);
        BeanTest beanTest = (BeanTest) applicationContext.getBean("beanTest");
        log.info("beanTest.name:{};beanTest.age:{}",beanTest.getName(),beanTest.getAge());
        applicationContext.close();

        //使用反射机制创建对象
        Class beanTestClass = BeanTest.class;
        BeanTest o = (BeanTest) beanTestClass.newInstance();

        //调用有参构造
        Constructor declaredConstructor = beanTestClass.getDeclaredConstructor(Integer.class, String.class);
        BeanTest zhoushuai = (BeanTest) declaredConstructor.newInstance(12, "zhoushuai111");
        System.out.println(zhoushuai.getName());

        //获取无参构造方法
        Constructor con2 = beanTestClass.getDeclaredConstructor();
        Object newObj2 = con2.newInstance();
        System.out.println(newObj2);
    }

}
