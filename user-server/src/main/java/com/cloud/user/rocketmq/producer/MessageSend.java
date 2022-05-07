package com.cloud.user.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.user.domain.entity.User;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.rocketmq.producer.async.TokenUserAsyncSendExecutor;
import com.cloud.user.rocketmq.producer.transaction.TokenUserTransactionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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
    private TokenUserTransactionExecutor tokenUserTransactionExecutor;

    @Autowired
    private TokenUserAsyncSendExecutor tokenUserAsyncSendExecutor;


    public void sendMessage() {
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        cloudMQTemplate.send(PlatformCommonConstant.Topic.USER_SERVER_TOPIC, "EC_USER_SERVER", userList);
    }

    public void asyncSendMessage() {
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        tokenUserAsyncSendExecutor.asyncSend(userList);
    }

    public void sendTransactionMessage() {
        User user = userMapper.selectById("1");
        tokenUserTransactionExecutor.send(user, user.getId());
    }


}
