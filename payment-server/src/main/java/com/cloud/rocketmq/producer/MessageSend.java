package com.cloud.rocketmq.producer;

import com.cloud.dao.UserMapper;
import com.cloud.entity.UserTest;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.rocketmq.producer.transaction.UserTransactionExecutor;
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
    private UserMapper userMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private UserTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage() {
        UserTest user = userMapper.selectById("1");
        cloudMQTemplate.send(PlatformCommonConstant.Topic.PAYMENT_SERVER_TOPIC, "EC_PAYMENT_SERVER", user);
    }

    public void sendTransactionMessage() {
        UserTest user = userMapper.selectById("1");
        tokenUserTransactionExecutor.send(user, user.getId());
    }


}
