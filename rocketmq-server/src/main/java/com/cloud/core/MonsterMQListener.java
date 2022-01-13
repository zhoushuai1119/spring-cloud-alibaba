package com.cloud.core;


import com.cloud.dto.MonsterMessage;

/**
 * MQ消息监听
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/3/15
 * @Time 下午9:21
 */
public interface MonsterMQListener<T> {
    /**
     * 接收消息.
     *
     * @param message
     */
    void onMessage(MonsterMessage<T> message) throws Exception;
}
