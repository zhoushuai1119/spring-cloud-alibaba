package com.xxl.job.admin.core.mqtopic;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/18 16:42
 * @version: v1
 */
public class RocketmqTopic {

    /**
     * 定时任务发送的 TOPIC
     */
    public interface Topic {
        String TIME_TASK_TOPIC = "TP_F_SC";
    }

    /**
     * 执行器
     */
    public interface executorHandler {
        String EXECUTOR_HANDLER = "mqJobHandler";
    }

}
