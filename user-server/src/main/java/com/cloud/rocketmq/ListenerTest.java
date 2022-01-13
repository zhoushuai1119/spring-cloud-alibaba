package com.cloud.rocketmq;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.core.TopicListener;
import com.cloud.dto.MonsterMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = "TP_EMS", eventCode = "EM_MSN")
public class ListenerTest implements TopicListener<String> {

    @Override
    public void onMessage(MonsterMessage<String> message) throws Exception {
        log.info("接收到消息:{}", message.getPayload());
    }

}
