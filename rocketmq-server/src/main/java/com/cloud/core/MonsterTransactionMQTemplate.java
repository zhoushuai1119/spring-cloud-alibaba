
package com.cloud.core;


import com.cloud.common.beans.response.BaseResponse;

/**
 * 消息发送模板接口
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/2/24
 * @Time 上午11:41
 */
public interface MonsterTransactionMQTemplate {
    /**
     * 同步发送
     *
     * @param topic     topic
     * @param eventCode eventCode
     * @param payload   消息体
     * @return 发送结果
     */
    BaseResponse<Object> send(String topic, String eventCode, Object payload);

    /**
     * 同步发送
     *
     * @param topic     topic
     * @param eventCode eventCode
     * @param payload   消息体
     * @param arg       Custom business parameter
     * @return 发送结果
     */
    BaseResponse<Object> send(String topic, String eventCode, Object payload, Object arg);

    /**
     * 同步发送
     *
     * @param topic     topic
     * @param eventCode eventCode
     * @param key key
     * @param payload   消息体
     * @param arg       Custom business parameter
     * @return 发送结果
     */
    BaseResponse<Object> send(String topic, String eventCode, String key, Object payload, Object arg);

}
