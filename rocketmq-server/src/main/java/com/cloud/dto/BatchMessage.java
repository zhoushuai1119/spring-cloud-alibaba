package com.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 批量消息
 *
 * @Author 李文举
 * @Date 2020/10/17 10:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchMessage {

    /**
     * topic
     */
    String topic;

    /**
     * 超时毫秒数
     */
    Integer timeoutMs;

    /**
     * HASH对象
     */
    Object hashBy;

    /**
     * 消息列表
     */
    Collection<MQMessage> mqMessages;
}
