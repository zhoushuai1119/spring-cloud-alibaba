package com.cloud.payment.rocketmq.producer;

import com.cloud.payment.rocketmq.producer.transaction.UserTransactionExecutor;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 17:08
 * @version: v1
 */
@Slf4j
@Component
public class MessageSend {

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private UserTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage() {
        cloudMQTemplate.send(PlatformCommonConstant.Topic.PAYMENT_SERVER_TOPIC, "EC_PAYMENT_SERVER", "payment-test");
    }

    public void sendTransactionMessage() {
        tokenUserTransactionExecutor.send("paymnent-Transaction-message","111");
    }


}
