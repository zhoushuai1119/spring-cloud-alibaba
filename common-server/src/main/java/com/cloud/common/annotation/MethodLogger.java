package com.cloud.common.annotation;

import com.cloud.common.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:58
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodLogger {

    LogTypeEnum logType() default LogTypeEnum.FULL;

}
