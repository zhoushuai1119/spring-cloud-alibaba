package com.cloud.rabbitmq.fanout;

import com.cloud.common.utils.LogUtil;
import com.cloud.config.RabbitMQConfig;
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
public class FanoutSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LogUtil.logger("****FanoutSender****:"+correlationData,LogUtil.INFO_LEVEL,null);
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", msg,correlationData);
    }
}
