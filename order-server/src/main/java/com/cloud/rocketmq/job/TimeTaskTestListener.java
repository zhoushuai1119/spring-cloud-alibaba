package com.cloud.rocketmq.job;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.core.TopicListener;
import com.cloud.dto.CloudMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = "TP_F_SC", eventCode = "TIME_TASK_TEST", log = true)
public class TimeTaskTestListener implements TopicListener<String> {

    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("test mq time task");
    }

}
