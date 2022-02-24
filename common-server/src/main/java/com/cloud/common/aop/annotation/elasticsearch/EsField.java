package com.cloud.common.aop.annotation.elasticsearch;

import java.lang.annotation.*;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/24 11:25
 * @version: v1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EsField {

    /**
     * 字段类型
     * @return
     */
    String type() default "text";

    /**
     * 日期类型格式化
     * @return
     */
    String format() default "";

    /**
     * 中文分词器
     * @return
     */
    String analyzer() default "";

}
