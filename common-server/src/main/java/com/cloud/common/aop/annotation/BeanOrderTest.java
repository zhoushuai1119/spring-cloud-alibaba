package com.cloud.common.aop.annotation;

import java.lang.annotation.*;

/**
 * @Description: 测试AOP通知执行顺序注解
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:58
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BeanOrderTest {

    String value() default "";

}
