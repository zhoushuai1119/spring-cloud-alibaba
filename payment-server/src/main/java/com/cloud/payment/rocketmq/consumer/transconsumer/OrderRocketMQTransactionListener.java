package com.cloud.payment.rocketmq.consumer.transconsumer;

import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.constants.PlatformCommonConstant;
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
@ConsumeTopic(topic = PlatformCommonConstant.Topic.ORDER_SERVER_TOPIC_TRANSACTION, eventCode = "EC_ORDER_SERVER_TRANSACTION",log = true)
public class OrderRocketMQTransactionListener implements TopicListener<String> {


    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("接收到order server事务消息:{}", JsonUtil.toString(message));
    }

}
