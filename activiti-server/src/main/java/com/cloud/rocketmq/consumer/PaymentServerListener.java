package com.cloud.rocketmq.consumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.payment.User;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.TopicListener;
import com.cloud.dto.MonsterMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.PAYMENT_SERVER_TOPIC, eventCode = "EC_PAYMENT_SERVER", log = true)
public class PaymentServerListener implements TopicListener<List<User>> {

    @Override
    public void onMessage(MonsterMessage<List<User>> message) {
        List<User> userList = message.getPayload();
        log.info("接收到{}服务消息:{}",CommonConstant.topic.PAYMENT_SERVER_TOPIC,
                JsonUtil.toString(userList));
    }

}
