package com.cloud.core;

import com.cloud.annotation.TansactionTopic;
import com.cloud.common.beans.response.BaseResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * @Author 马腾飞
 * @Date 2019/12/11
 * @Time 14:46
 * @Description
 */
@Data
@Slf4j
public abstract class BaseTransactionExecutor<T, R> implements TopicTransactionListener<T, R> {

    private String topic;
    private String eventCode;
    private RocketMQTransactionTemplate rocketMQTransactionTemplate;

    public BaseTransactionExecutor(RocketMQTransactionTemplate rocketMQTransactionTemplate) {
        this.rocketMQTransactionTemplate = rocketMQTransactionTemplate;
    }

    /**
     * 发送
     *
     * @param message  消息体
     * @param localArg 本地事务参数
     * @return 发送结果
     */
    public BaseResponse<Object> send(T message, R localArg) {
        return rocketMQTransactionTemplate.send(topic, eventCode, message, localArg);
    }

    /**
     * 发送消息 指定key
     *
     * @param message 消息体
     * @return 发送结果
     */
    public BaseResponse<Object> send(T message, R localArg, String key) {
        return rocketMQTransactionTemplate.send(topic, eventCode, key, message, localArg);
    }

    @PostConstruct
    void init() throws Exception {
        TansactionTopic annotation = this.getClass().getAnnotation(TansactionTopic.class);
        topic = annotation.topic();
        eventCode = annotation.eventCode();
        DefaultTopicTransactionListenerImpl.registerListener(this);
    }

}
