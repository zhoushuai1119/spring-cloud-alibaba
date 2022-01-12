package com.cloud.rabbitmq.direct;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.entity.product.Product;
import com.cloud.config.RabbitMQConfig;
import com.cloud.dao.ProductMapper;
import com.cloud.rabbitmq.template.MonsterMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
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
    private MonsterMQTemplate monsterMQTemplate;
    @Autowired
    private ProductMapper productMapper;

    public void send(Object msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("****DirectSender****:"+correlationData);
        List<Product> productList = productMapper.selectList(new QueryWrapper<>());
        this.monsterMQTemplate.send(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.DIRECT_QUEUE_ROUTYKEY, productList ,correlationData);
    }

}
