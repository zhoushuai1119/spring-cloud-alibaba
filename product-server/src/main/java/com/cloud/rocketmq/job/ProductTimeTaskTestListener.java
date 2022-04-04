package com.cloud.rocketmq.job;

import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.constants.PlatformCommonConstant;
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
@ConsumeTopic(topic = PlatformCommonConstant.ScheduledJobTopic.SCHEDULED_JOB_TOPIC, eventCode = "EC_TASK_PRODUCT_JOB_TEST", log = true)
public class ProductTimeTaskTestListener implements TopicListener<String> {

    @Override
    public void onMessage(CloudMessage<String> message) {
        log.info("product server time task mq test");
    }

}
