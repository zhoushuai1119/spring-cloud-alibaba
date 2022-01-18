package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.core.CloudMQTemplate;
import com.cloud.dao.ShiroUserMapper;
import com.cloud.rocketmq.producer.transaction.ShiroUserTransactionExecutor;
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
    private ShiroUserMapper shiroUserMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private ShiroUserTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage(){
        List<ShiroUser> shiroUserList = shiroUserMapper.selectList(new QueryWrapper<>());
        cloudMQTemplate.send(CommonConstant.topic.ACTIVITI_SERVER_TOPIC,"EC_ACTIVITI_SERVER",shiroUserList);
    }

    public void sendTransactionMessage(){
        List<ShiroUser> shiroUserList = shiroUserMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(shiroUserList)) {
            shiroUserList.forEach(shiroUser -> {
                tokenUserTransactionExecutor.send(shiroUser,shiroUser.getId());
            });
        }
    }


}
