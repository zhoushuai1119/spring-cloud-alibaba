package com.cloud.core;


import com.cloud.dto.MonsterMessage;

/**
 * 分topic消费的接口
 *
 */
public interface TopicListener<T> {

    /**
     * 接收消息.
     *
     * @param message
     */
    void onMessage(MonsterMessage<T> message) throws Exception;
}
