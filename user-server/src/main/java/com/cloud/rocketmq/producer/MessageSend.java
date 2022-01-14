package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.core.MonsterMQTemplate;
import com.cloud.dao.TokenUserMapper;
import com.cloud.rocketmq.transconsumer.executor.ConsumerTransactionExecutor;
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
    private TokenUserMapper tokenUserMapper;

    @Autowired
    private MonsterMQTemplate monsterMQTemplate;

    @Autowired
    private ConsumerTransactionExecutor consumerTransactionExecutor;

    public void sendMessage(){
        List<TokenUser> tokenUserList = tokenUserMapper.selectList(new QueryWrapper<>());
        monsterMQTemplate.send(CommonConstant.topic.USER_SERVER_TOPIC,"EC_USER_SERVER",tokenUserList);
    }

    public void sendTransactionMessage(){
        for (int i = 0; i < 10; i++) {
            consumerTransactionExecutor.send("transaction test" + i,i);
        }
    }

}
