package com.cloud.common.aop.annotation;


import java.lang.annotation.*;

/**
 * 权限3.0
 *
 * @author qiankx
 * @date 2021-09-07 15:01
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface NewAuthV2 {

    String agentNoProperty() default "agentNo";

}
