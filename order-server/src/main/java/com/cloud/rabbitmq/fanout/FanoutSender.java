package com.cloud.rabbitmq.fanout;

import com.cloud.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.LogUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author： Zhou Shuai
 * @Date： 16:18 2019/1/10
 * @Description：
 * @Version:  01
 */
@Component
@Slf4j
public class FanoutSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("****FanoutSender****:"+correlationData);
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", msg,correlationData);
    }
}
