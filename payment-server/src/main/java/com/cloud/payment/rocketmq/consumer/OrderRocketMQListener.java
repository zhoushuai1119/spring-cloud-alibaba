package com.cloud.payment.rocketmq.consumer;


import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.platform.rocketmq.annotation.ConsumeTopic;
import com.cloud.platform.rocketmq.core.TopicListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/31 15:20
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = PlatformCommonConstant.Topic.ORDER_SERVER_TOPIC, eventCode = "EC_ORDER_SERVER", log = true)
public class OrderRocketMQListener implements TopicListener<String> {

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
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(CloudMessage<String> message) {
        log.info("接收到order server普通消息:{}", JsonUtil.toString(message));
    }

}
