package com.cloud.rocketmq.job;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.utils.JsonUtil;
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
@ConsumeTopic(topic = "TP_F_SC", eventCode = "EC_TASK_ORDER_JOB_TEST", log = true)
public class OrderTimeTaskTestListener implements TopicListener<String> {

    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("message:{}", JsonUtil.toString(message));
        log.info("order server time task mq test");
    }

}
