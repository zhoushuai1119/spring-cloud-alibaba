package com.cloud.order.controller;

import com.cloud.order.rocketmq.producer.MessageSend;
import com.cloud.platform.common.domain.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 17:19
 * @version: v1
 */
@RestController
@RequestMapping("rocketmq")
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
     * 发送事务消息
     * @return
     */
    @PostMapping("/send/transaction")
    public BaseResponse sendTransactionMessage(){
        messageSend.sendTransactionMessage();
        return BaseResponse.createSuccessResult(null);
    }

}