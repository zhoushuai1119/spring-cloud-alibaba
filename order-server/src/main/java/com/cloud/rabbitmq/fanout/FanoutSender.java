package com.cloud.rabbitmq.fanout;

import com.cloud.config.RabbitMQConfig;
import com.cloud.rabbitmq.core.MonsterMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
    private MonsterMQTemplate monsterMQTemplate;

    public void send(Object msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("****FanoutSender****:"+correlationData);
        monsterMQTemplate.send(RabbitMQConfig.FANOUT_EXCHANGE, msg,correlationData);
    }
}
