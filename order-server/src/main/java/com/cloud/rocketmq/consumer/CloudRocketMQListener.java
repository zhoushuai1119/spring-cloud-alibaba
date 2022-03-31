package com.cloud.rocketmq.consumer;

import com.cloud.common.constants.CommonConstant;
import com.cloud.entity.Category;
import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.platform.rocketmq.annotation.ConsumeTopic;
import com.cloud.platform.rocketmq.core.TopicListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/31 15:20
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.ORDER_SERVER_TOPIC, eventCode = "EC_ORDER_SERVER", log = true)
public class CloudRocketMQListener implements TopicListener<Category> {

    @Override
    public void onMessage(CloudMessage<Category> message) {
        log.info("接收到消息:{}", JsonUtil.toString(message.getPayload()));
    }

}
