package com.cloud.rocketmq.consumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.user.TokenUser;
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
@ConsumeTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER", log = true)
public class UserServerListener implements TopicListener<List<TokenUser>> {

    @Override
    public void onMessage(MonsterMessage<List<TokenUser>> message) {
        List<TokenUser> tokenUserList = message.getPayload();
        log.info("接收到{}服务消息:{}",CommonConstant.topic.USER_SERVER_TOPIC,
                JsonUtil.toString(tokenUserList));
    }

}
