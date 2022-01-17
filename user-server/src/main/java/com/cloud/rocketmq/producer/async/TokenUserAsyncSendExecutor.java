package com.cloud.rocketmq.producer.async;

import com.cloud.annotation.AsyncSendTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.core.AsyncSendExecutor;
import com.cloud.core.RocketMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;

import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/17 18:53
 * @version: v1
 */
@Slf4j
@AsyncSendTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER")
public class TokenUserAsyncSendExecutor extends AsyncSendExecutor<List<TokenUser>> {

    public TokenUserAsyncSendExecutor(RocketMQTemplate rocketMQTemplate) {
        super(rocketMQTemplate);
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("on success");
    }

    @Override
    public void onException(Throwable throwable) {
        log.info("on exception");
    }

}
