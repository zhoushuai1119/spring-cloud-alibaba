package com.cloud.rabbitmq.template;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2021/10/12 13:55
 * @version: v1
 */
public interface MonsterMQTemplate {

    void send(String var1, Object var2);

    void send(String var1, Object var2, CorrelationData var3);

    void send(String var1, String var2, Object var3);

    void send(String var1, String var2, Object var3, CorrelationData var4);

}
