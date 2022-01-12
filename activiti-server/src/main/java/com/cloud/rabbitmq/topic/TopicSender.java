package com.cloud.rabbitmq.topic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.entity.activiti.Leave;
import com.cloud.config.RabbitMQConfig;
import com.cloud.dao.LeaveMapper;
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
 * @Date： 16:26 2019/1/10
 * @Description：
 * @Version:  01
 */
@Component
@Slf4j
public class TopicSender{

    @Resource
    private MonsterMQTemplate monsterMQTemplate;
    @Autowired
    private LeaveMapper leaveMapper;

    public void send(Object msg) {
        List<Leave> leaveList = leaveMapper.selectList(new QueryWrapper<>());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("****TopicSender****:"+correlationData);
        /*声明消息处理器  这个对消息进行处理  可以设置一些参数
        对消息进行一些定制化处理   我们这里  来设置消息的编码
        以及消息的过期时间  因为在.net 以及其他版本过期时间不一致
        这里的时间毫秒值 为字符串*/
        /*MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
        //设置编码
            messageProperties.setContentEncoding("utf-8");
        //设置过期时间10*1000毫秒
            messageProperties.setExpiration("30000");
            return message;
        };*/
        monsterMQTemplate.send(RabbitMQConfig.TOPIC_EXCHANGE,"lzc.zhou.test",leaveList,correlationData);
    }

}
