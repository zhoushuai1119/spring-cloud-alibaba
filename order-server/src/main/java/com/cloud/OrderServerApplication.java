package com.cloud;

import com.cloud.entity.BeanTest;
import com.cloud.entity.Car;
import com.cloud.entity.CarFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

//自动装配的方法在 AutoConfigurationImportSelector.getAutoConfigurationEntry
//而不是 AutoConfigurationImportSelector.selectImports
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//启用@WebServlet、@WebFilter和@WebListener注释的类的自动注册
@ServletComponentScan
@EnableAsync
@Slf4j
public class OrderServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OrderServerApplication.class, args);
        BeanTest beanTest = (BeanTest) run.getBean("beanTest");
        log.info("修改后的beanTest的属性name:{}; age:{}", beanTest.getName(), beanTest.getAge());

        //Spring容器就调用接口方法CarFactoryBean#getObject()方法返回
        Car car = (Car) run.getBean("carFactoryBean");

        //如果希望获取CarFactoryBean的实例，则需要在使用getBean(beanName)
        // 方法时在beanName前显示的加上"&"前缀：如getBean("&car");
        CarFactoryBean carFactoryBean = (CarFactoryBean) run.getBean("&carFactoryBean");

        //run.close();
    }

}

