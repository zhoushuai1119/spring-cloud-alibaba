package com.cloud.rocketmq.producer;

import com.cloud.common.constants.CommonConstant;
import com.cloud.dao.UserMapper;
import com.cloud.entity.User;
import com.cloud.mq.base.core.CloudMQTemplate;
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
        User user = userMapper.selectById("1");
        cloudMQTemplate.send(CommonConstant.topic.PAYMENT_SERVER_TOPIC, "EC_PAYMENT_SERVER", user);
    }

    public void sendTransactionMessage() {
        User user = userMapper.selectById("1");
        tokenUserTransactionExecutor.send(user, user.getId());
    }


}
