package com.cloud.rocketmq;

import com.cloud.annotation.ConsumeTopic;
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
@ConsumeTopic(topic = "TP_EMS", eventCode = "EM_MSN", log = true)
public class ListenerTest implements TopicListener<List<TokenUser>> {

    @Override
    public void onMessage(MonsterMessage<List<TokenUser>> message) {
        List<TokenUser> userList = message.getPayload();
        log.info("接收到消息:{}",
                JsonUtil.toString(userList));
    }

}
