package com.cloud.rocketmq.consumer;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.TopicListener;
import com.cloud.dto.MonsterMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER")
public class UserServerListener implements TopicListener<List<TokenUser>> {

    /**
     * 细心的读者应该能看到,这里的onMessage方法是void类型的,没有返回状态,与我们平时用的不一样,
     * 那如果消费失败,怎么返回RECONSUME_LATER的状态呢,
     * github上官方是回复throw exception的时候会自动处理消息返回RECONSUME_LATER,
     * 直接抛出RuntimeException即可
     * ————————————————
     * 原文链接：https://blog.csdn.net/weixin_43765598/article/details/114874531
     * @param message
     */
    @Override
    public void onMessage(MonsterMessage<List<TokenUser>> message) {
//        log.info("重试次数:{}",message.getReconsumeTimes());
        List<TokenUser> tokenUserList = message.getPayload();
        log.info("接收到{}服务:消息:{}",CommonConstant.topic.USER_SERVER_TOPIC,
                JsonUtil.toString(tokenUserList));
    }

}
