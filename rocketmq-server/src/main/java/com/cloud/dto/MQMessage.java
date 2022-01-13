package com.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批量消息-消息内容
 *
 * @Author 李文举
 * @Date 2020/10/17 10:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQMessage {

    /**
     * eventCode
     */
    String eventCode;

    /**
     * key
     */
    String key;

    /**
     * 消息内容
     */
    Object payload;
}
