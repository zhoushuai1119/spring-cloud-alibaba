package com.cloud.rabbitmq.direct;

import com.cloud.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author： Zhou Shuai
 * @Date： 16:24 2019/1/10
 * @Description：
 * @Version:  01
 */
@Component
@Slf4j
public class DirectSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("****DirectSender****:"+correlationData);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.DIRECT_QUEUE_ROUTYKEY, msg ,correlationData);
    }

}
