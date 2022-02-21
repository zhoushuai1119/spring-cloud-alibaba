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

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/18 16:47
 * @version: v1
 */
@Component
@Slf4j
public class MqJobHandler {

    private ThreadPoolExecutor mqJobHandlerPool;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @PostConstruct
    public void init() {
        mqJobHandlerPool = new ThreadPoolExecutor(
                5,
                10,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "xxl-job-admin, mqJobHandlerPool-" + r.hashCode());
                    }
                });
    }

    /**
     * 定时任务发送MQ
     */
    @XxlJob(RocketmqTopic.executorHandler.EXECUTOR_HANDLER)
    public void mqJobHandler() {
        mqJobHandlerPool.execute(() -> {
            String executorParams = XxlJobHelper.getJobParam();

            if (StringUtils.isBlank(executorParams)) {
                return;
            }

            log.info("executorParams:{}",executorParams);

            ExecutorParamsDTO params = JsonUtil.toBean(executorParams,ExecutorParamsDTO.class);

            if (Objects.isNull(params) || StringUtils.isBlank(params.getEventCode()) || Objects.isNull(params.getLogId())) {
                return;
            }

            TimeBasedJobMessage timeBasedJobMessage = new TimeBasedJobMessage(params.getLogId(),System.currentTimeMillis());
            BaseResponse<Object> sendResult = cloudMQTemplate.send(RocketmqTopic.Topic.TIME_TASK_TOPIC,params.getEventCode(), timeBasedJobMessage);

            if (!sendResult.isSuccess()) {
                XxlJobHelper.handleFail();
            }

            XxlJobHelper.log("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", RocketmqTopic.Topic.TIME_TASK_TOPIC, params.getEventCode(), JsonUtil.toString(timeBasedJobMessage));

            log.info("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", RocketmqTopic.Topic.TIME_TASK_TOPIC,params.getEventCode(), JsonUtil.toString(timeBasedJobMessage));
            // default success
            XxlJobHelper.handleSuccess();
        });
    }

}
