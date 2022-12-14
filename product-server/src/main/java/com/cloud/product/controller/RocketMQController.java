package com.cloud.product.controller;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.product.rocketmq.producer.MessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/15 20:22
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
