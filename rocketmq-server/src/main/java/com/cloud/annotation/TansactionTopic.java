package com.cloud.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 事务消息 topic eventcode
 *
 * @Author 马腾飞
 * @Date 2019/9/6 16:49
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TansactionTopic {

    /**
     * topic
     *
     * @return
     */
    String topic();

    /**
     * eventCode
     *
     * @return
     */
    String eventCode();

    /**
     * 是否打印日志
     * @return
     */
    boolean log() default false;

}
