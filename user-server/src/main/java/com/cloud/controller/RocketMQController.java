package com.cloud.controller;

import com.cloud.platform.common.response.BaseResponse;
import com.cloud.rocketmq.producer.MessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 17:19
 * @version: v1
 */
@RestController
public class RocketMQController {

    @Autowired
    private MessageSend messageSend;

    /**
     * 发送普通消息
     * @return
     */
    @PostMapping("/send")
    public BaseResponse sendMessage(){
        messageSend.sendMessage();
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 发送异步消息
     * @return
     */
    @PostMapping("/async/send")
    public BaseResponse asyncSendMessage(){
        messageSend.asyncSendMessage();
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 发送事务消息
     * @return
     */
    @PostMapping("/send/transaction")
    public BaseResponse sendTransactionMessage(){
        messageSend.sendTransactionMessage();
        return BaseResponse.createSuccessResult(null);
    }

}
