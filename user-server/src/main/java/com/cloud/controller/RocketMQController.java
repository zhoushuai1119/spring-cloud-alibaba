package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.rocketmq.MessageSend;
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

    @PostMapping("/send")
    public BaseResponse rocketMqTest(){
        messageSend.sendMessageTest();
        return BaseResponse.createSuccessResult(null);
    }

}
