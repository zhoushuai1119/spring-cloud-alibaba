package com.cloud.core;


import com.cloud.dto.MonsterMessage;

/**
 * MQ消息监听
 * @param <T>
 */
public interface CloudMQListener<T> {
    /**
     * 接收消息.
     *
     * @param message
     */
    void onMessage(MonsterMessage<T> message) throws Exception;
}
