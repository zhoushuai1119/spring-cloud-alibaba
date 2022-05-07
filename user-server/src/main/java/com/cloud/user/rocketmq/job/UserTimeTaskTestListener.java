package com.cloud.user.rocketmq.job;

import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.rocketmq.annotation.ConsumeTopic;
import com.cloud.platform.rocketmq.core.TopicListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = "TP_F_SC", eventCode = "EC_TASK_USER_JOB_TEST", log = true)
public class UserTimeTaskTestListener implements TopicListener<String> {

    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("user server time task mq test");
    }

}
