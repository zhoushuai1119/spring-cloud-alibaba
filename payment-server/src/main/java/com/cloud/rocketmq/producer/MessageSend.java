package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.payment.User;
import com.cloud.core.CloudMQTemplate;
import com.cloud.dao.UserMapper;
import com.cloud.rocketmq.producer.transaction.UserTransactionExecutor;
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
    private UserMapper userMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private UserTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage(){
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        cloudMQTemplate.send(CommonConstant.topic.PAYMENT_SERVER_TOPIC,"EC_PAYMENT_SERVER",userList);
    }

    public void sendTransactionMessage(){
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(userList)) {
            userList.forEach(user -> {
                tokenUserTransactionExecutor.send(user,user.getId());
            });
        }
    }


}
