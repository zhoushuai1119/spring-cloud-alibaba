package com.cloud.handler;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.mqjob.ExecutorParamsDTO;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.CloudMQTemplate;
import com.cloud.timedjob.TimeBasedJobMessage;
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
    @XxlJob(CommonConstant.executorHandler.EXECUTOR_HANDLER)
    public void mqJobHandler() {
        mqJobHandlerPool.execute(() -> {

            String executorParams = XxlJobHelper.getJobParam();

            if (StringUtils.isBlank(executorParams)) {
                return;
            }

            log.info("executorParams:{}",executorParams);

            ExecutorParamsDTO executorParamsDTO = JsonUtil.toBean(executorParams,ExecutorParamsDTO.class);

            if (Objects.isNull(executorParamsDTO) || StringUtils.isEmpty(executorParamsDTO.getEventCode()) || Objects.isNull(executorParamsDTO.getLogId())) {
                return;
            }


            TimeBasedJobMessage timeBasedJobMessage = new TimeBasedJobMessage(executorParamsDTO.getLogId(),System.currentTimeMillis());
            BaseResponse<Object> sendResult = cloudMQTemplate.send(CommonConstant.TimeTaskTopic.TIME_TASK_TOPIC,executorParamsDTO.getEventCode(), timeBasedJobMessage);

            if (!sendResult.isSuccess()) {
                XxlJobHelper.handleFail();
            }

            XxlJobHelper.log("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", CommonConstant.TimeTaskTopic.TIME_TASK_TOPIC, executorParamsDTO.getEventCode(), JsonUtil.toString(timeBasedJobMessage));

            log.info("XXL-JOB发送RockrtMQ,Topic:{},eventCode:{},messageBody:{}", CommonConstant.TimeTaskTopic.TIME_TASK_TOPIC,executorParamsDTO.getEventCode(), JsonUtil.toString(timeBasedJobMessage));
            // default success
            XxlJobHelper.handleSuccess();
        });
    }

}
