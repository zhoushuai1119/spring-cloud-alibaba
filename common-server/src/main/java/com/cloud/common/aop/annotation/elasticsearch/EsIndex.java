package com.cloud.common.aop.annotation.elasticsearch;

import java.lang.annotation.*;

/**
 * @description: es 索引注解
 * @author: zhou shuai
 * @date: 2022/1/8 13:21
 * @version: v1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface EsIndex {
    String value() default "";
}
