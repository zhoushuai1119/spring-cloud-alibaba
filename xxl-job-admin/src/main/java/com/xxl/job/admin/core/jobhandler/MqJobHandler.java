package com.xxl.job.admin.core.jobhandler;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.CloudMQTemplate;
import com.cloud.timedjob.TimeBasedJobMessage;
import com.xxl.job.admin.core.mqtopic.RocketmqTopic;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/18 16:47
 * @version: v1
 */
@Component
@Slf4j
public class MqJobHandler {

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    /**
     * 定时任务发送MQ
     */
    @XxlJob(RocketmqTopic.executorHandler.EXECUTOR_HANDLER)
    public void mqJobHandler() {

        String eventCode = XxlJobHelper.getJobParam();
        if (StringUtils.isBlank(eventCode)) {
            return;
        }

        TimeBasedJobMessage timeBasedJobMessage = new TimeBasedJobMessage(11L,System.currentTimeMillis());
        BaseResponse<Object> sendResult = cloudMQTemplate.send(RocketmqTopic.Topic.TIME_TASK_TOPIC,eventCode, timeBasedJobMessage);

        if (!sendResult.isSuccess()) {
            XxlJobHelper.handleFail();
        }

        XxlJobHelper.log("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", RocketmqTopic.Topic.TIME_TASK_TOPIC, eventCode, JsonUtil.toString(timeBasedJobMessage));

        log.info("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", RocketmqTopic.Topic.TIME_TASK_TOPIC,eventCode, JsonUtil.toString(timeBasedJobMessage));
        // default success
        XxlJobHelper.handleSuccess();
    }

}
