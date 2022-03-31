package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.dao.TokenUserMapper;
import com.cloud.entity.TokenUser;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.rocketmq.producer.async.TokenUserAsyncSendExecutor;
import com.cloud.rocketmq.producer.transaction.TokenUserTransactionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
    private TokenUserMapper tokenUserMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private TokenUserTransactionExecutor tokenUserTransactionExecutor;

    @Autowired
    private TokenUserAsyncSendExecutor tokenUserAsyncSendExecutor;


    public void sendMessage() {
        List<TokenUser> tokenUserList = tokenUserMapper.selectList(new QueryWrapper<>());
        cloudMQTemplate.send(CommonConstant.topic.USER_SERVER_TOPIC, "EC_USER_SERVER", tokenUserList);
    }

    public void asyncSendMessage() {
        List<TokenUser> tokenUserList = tokenUserMapper.selectList(new QueryWrapper<>());
        tokenUserAsyncSendExecutor.asyncSend(tokenUserList);
    }

    public void sendTransactionMessage() {
        TokenUser tokenUser = tokenUserMapper.selectById("1");
        tokenUserTransactionExecutor.send(tokenUser, tokenUser.getId());
    }


}
