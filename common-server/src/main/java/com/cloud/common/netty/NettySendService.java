package com.cloud.common.netty;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-15 11:11
 */
public interface NettySendService<T> {

    /**
     * netty发送消息
     * @param t
     */
    void sendMessage(T t);

}
