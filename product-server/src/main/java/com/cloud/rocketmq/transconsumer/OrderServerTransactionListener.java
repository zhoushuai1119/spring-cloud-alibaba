package com.cloud.rocketmq.transconsumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.order.Category;
import com.cloud.core.TopicListener;
import com.cloud.dto.MonsterMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/14 15:34
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.ORDER_SERVER_TOPIC_TRANSACTION, eventCode = "EC_ORDER_SERVER_TRANSACTION",log = true)
public class OrderServerTransactionListener implements TopicListener<Category> {


    @Override
    public void onMessage(MonsterMessage<Category> message) {
        Category category = message.getPayload();
        log.info("监听到{}服务事务消息:{}",CommonConstant.topic.ORDER_SERVER_TOPIC,
                category);
    }

}
