package com.cloud.dto;

import lombok.Data;

/**
 * 消息
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/2/26
 * @Time 上午10:38
 */
@Data
public class MonsterMessage<T> {
    /**
     * 消息id
     */
    String messageId;
    /**
     * topic
     */
    String topic;

    /**
     * key
     */
    String key;

    /**
     * eventCode
     */
    String eventCode;

    /**
     * 生成时间戳
     */
    long createTimeStamp;
    /**
     * 重试次数
     */
    int reconsumeTimes;

    /**
     * 消息id
     */
    String transactionId;

    /**
     * 消息内容
     */
    T payload;
}
