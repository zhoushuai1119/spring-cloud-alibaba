package com.cloud.rocketmq.transconsumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.core.TopicListener;
import com.cloud.dto.CloudMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/14 15:34
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC_TRANSACTION, eventCode = "EC_USER_SERVER_TRANSACTION",log = true)
public class UserServerTransactionListener implements TopicListener<TokenUser> {


    @Override
    public void onMessage(CloudMessage<TokenUser> message) {
        TokenUser tokenUser = message.getPayload();
        log.info("监听到{}服务事务消息:{}",CommonConstant.topic.USER_SERVER_TOPIC,
                tokenUser);
    }

}
