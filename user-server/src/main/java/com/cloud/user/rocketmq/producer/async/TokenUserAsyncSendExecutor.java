package com.cloud.user.rocketmq.producer.async;

import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.rocketmq.annotation.AsyncSendTopic;
import com.cloud.platform.rocketmq.core.AsyncSendExecutor;
import com.cloud.platform.rocketmq.core.RocketMQTemplate;
import com.cloud.user.domain.entity.User;
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
@AsyncSendTopic(topic = PlatformCommonConstant.Topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER")
public class TokenUserAsyncSendExecutor extends AsyncSendExecutor<List<User>> {

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
