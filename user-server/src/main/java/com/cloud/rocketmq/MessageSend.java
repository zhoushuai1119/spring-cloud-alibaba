package com.cloud.rocketmq;

import com.cloud.core.MonsterMQTemplate;
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
    private MonsterMQTemplate monsterMQTemplate;

    public void sendMessageTest(){
        monsterMQTemplate.send("TP_EMS","EM_MSN","1111");
    }

}
