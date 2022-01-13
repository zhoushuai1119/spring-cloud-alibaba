package com.cloud.core;


import com.cloud.dto.MonsterMessage;

/**
 * 分topic消费的接口
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/5/24
 * @Time 下午10:06
 */
public interface TopicListener<T> {

    /**
     * 接收消息.
     *
     * @param message
     */
    void onMessage(MonsterMessage<T> message) throws Exception;
}
