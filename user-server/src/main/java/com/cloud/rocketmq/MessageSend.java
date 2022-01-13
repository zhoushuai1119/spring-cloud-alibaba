package com.cloud.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.core.MonsterMQTemplate;
import com.cloud.dao.TokenUserMapper;
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

    public void sendMessageTest(){
        List<TokenUser> tokenUserList = tokenUserMapper.selectList(new QueryWrapper<>());
        monsterMQTemplate.send("TP_EMS","EM_MSN",tokenUserList);
    }

}
