package com.xxl.job.admin.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.core.CloudMQTemplate;
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
public class MqJobHandlerController {

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    /**
     * 定时任务发送MQ
     */
    @XxlJob("mqJobHandler")
    public void mqJobHandler() {

        String eventCode = XxlJobHelper.getJobParam();
        if (StringUtils.isBlank(eventCode)) {
            return;
        }

        XxlJobHelper.log("XXL-JOB发送MQ,Topic:{},eventCode:{}", RocketmqTopic.Topic.TIME_TASK_TOPIC, eventCode);

        BaseResponse<Object> sendResult = cloudMQTemplate.send(RocketmqTopic.Topic.TIME_TASK_TOPIC,eventCode,null);

        if (!sendResult.isSuccess()) {
            XxlJobHelper.handleFail();
        }
        // default success
        XxlJobHelper.handleSuccess();
    }

}
