package com.cloud.rabbitmq.direct;

import com.cloud.common.entity.order.Category;
import com.cloud.common.utils.LogUtil;
import com.cloud.config.RabbitMQConfig;
import com.cloud.dao.CategoryMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DirectSender {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CategoryMapper categoryDao;

    public void send(String msg) {
        Category category = categoryDao.selectById("1");
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LogUtil.logger("****DirectSender****:"+correlationData,LogUtil.INFO_LEVEL,null);
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, "1111eeeeeee11", msg ,correlationData);
    }

}
