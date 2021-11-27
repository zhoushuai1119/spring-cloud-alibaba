package com.cloud.service;

import com.cloud.rabbitmq.direct.DirectSender;
import com.cloud.rabbitmq.fanout.FanoutSender;
import com.cloud.rabbitmq.topic.TopicSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : shuaizhou4
 * @version : [V1.0]
 * @description:
 * @Date ： 2020-01-13 18:50
 */
@Service
@Slf4j
public class RabbitService {

    @Autowired
    private TopicSender topicSender;
    @Autowired
    private FanoutSender fanoutSender;
    @Autowired
    private DirectSender directSender;

    public void directSender(String msg){
        directSender.send(msg);
    }

    public void fanoutSender(String msg){
        fanoutSender.send(msg);
    }

    public void topicSender(String msg){
        topicSender.send(msg);
    }

}
