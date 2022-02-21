package com.xxl.job.admin.core.mqfeedback;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.TopicListener;
import com.cloud.dto.CloudMessage;
import com.cloud.timedjob.TimeBasedJobFeedback;
import com.xxl.job.admin.core.mqtopic.RocketmqTopic;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = RocketmqTopic.FeedBackTopic.FEEDBACK_TASK_TOPIC,
        eventCode = RocketmqTopic.FeedBackTopic.FEEDBACK_TASK_EVENTCODE, log = true)
public class MqFeedBackListener implements TopicListener<TimeBasedJobFeedback> {

    @Override
    public void onMessage(CloudMessage<TimeBasedJobFeedback> feedbackMessage) {
        log.info("接收到定时任务回调消息:{}", JsonUtil.toString(feedbackMessage));
    }

}
