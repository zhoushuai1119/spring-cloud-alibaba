package com.cloud.common.aop.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 13:53
 * @version: v1
 */
@Inherited
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface EsDocPrivateKey {

}
