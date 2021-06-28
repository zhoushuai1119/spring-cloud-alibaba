package com.cloud.common.aop.annotation;

import com.cloud.common.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @Description: 打印请求和返回参数
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:58
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodLogger {

    LogTypeEnum logType() default LogTypeEnum.FULL;

}
