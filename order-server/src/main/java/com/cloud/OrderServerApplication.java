package com.cloud;

import com.cloud.common.entity.order.BeanTest;
import com.cloud.common.entity.order.Car;
import com.cloud.common.entity.order.CarFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//启用@WebServlet、@WebFilter和@WebListener注释的类的自动注册
@ServletComponentScan
@EnableAsync
//@RefreshScope加在启动类不会实时刷新配置，必须重启服务。
//在动态配置的地方添加@RefreshScope才可以动态获取配置信息
@RefreshScope
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

