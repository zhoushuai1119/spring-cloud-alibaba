package com.cloud.rocketmq.transconsumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
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
@ConsumeTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER_TRANSACTION",log = true)
public class ConsumerMessageListener implements TopicListener<String> {


    @Override
    public void onMessage(MonsterMessage<String> message) {
        String str = message.getPayload();
        log.info("事务消息监听到消息:{}",str);
    }

}
