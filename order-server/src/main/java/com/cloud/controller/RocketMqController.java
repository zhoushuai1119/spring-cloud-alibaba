package com.cloud.controller;

import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.order.Category;
import com.cloud.common.service.order.CategoryService;
import com.cloud.service.rocketmq.MySource;
import com.cloud.service.rocketmq.core.MonsterMQTemplate;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/rocketmq")
public class RocketMqController {

    @Autowired
    private MySource mySource;
    @Autowired
    private CategoryService categoryService;
    @Resource
    private MonsterMQTemplate monsterMQTemplate;

    @GetMapping("test-stream")
    public boolean testStream(){
        Category category = categoryService.getById("1");
        // <3>创建 Spring Message 对象
        Message<Category> springMessage = MessageBuilder.withPayload(category)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, "3") // 设置延迟级别为 3，10 秒后消费。
                .setHeader(MessageConst.PROPERTY_TAGS, "tag") // 设置 Tag
                .build();
        // <4>发送消息
        return mySource.erbadagangOutput().send(springMessage);
    }

    @GetMapping("test-rocketmq")
    public boolean test(){
        Category category = categoryService.getById("1");
        monsterMQTemplate.send(CommonConstant.RocketMQTopic.SCORE_TOPIC, CommonConstant.RocketMQTag.SCORE_EVENT, category);
        return true;
    }

}
