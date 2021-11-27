package com.cloud.common.rabbitmq.impl;

import com.cloud.common.rabbitmq.MonsterMQTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2021/10/12 14:00
 * @version: v1
 */
@Component
public class MonsterMQTemplateImpl implements MonsterMQTemplate {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String var1, Object var2) {
        this.sendMessage(var1, "", var2, (CorrelationData) null);
    }

    @Override
    public void send(String var1, Object var2, CorrelationData var3) {
        this.sendMessage(var1, "", var2, var3);
    }

    @Override
    public void send(String var1, String var2, Object var3) {
        this.sendMessage(var1, var2, var3,(CorrelationData) null);
    }

    @Override
    public void send(String var1, String var2, Object var3, CorrelationData var4) {
        this.sendMessage(var1, var2, var3,var4);
    }

    private void sendMessage(String exchange, String routingKey, Object object, CorrelationData correlationData) {
        Assert.hasText(exchange, "'exchange' is required");
        rabbitTemplate.convertAndSend(exchange, routingKey, object, correlationData);
    }

}
