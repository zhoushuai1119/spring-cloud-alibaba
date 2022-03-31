package com.cloud.rocketmq.consumer.transconsumer;

import com.cloud.common.constants.CommonConstant;
import com.cloud.entity.Product;
import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.platform.rocketmq.annotation.ConsumeTopic;
import com.cloud.platform.rocketmq.core.TopicListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/14 15:34
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.PRODUCT_SERVER_TOPIC_TRANSACTION, eventCode = "EC_PRODUCT_SERVER_TRANSACTION",log = true)
public class RocketMQTransactionListener implements TopicListener<String> {


    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("监听到事务消息:{}", JsonUtil.toString(message.getPayload()));
    }

}
