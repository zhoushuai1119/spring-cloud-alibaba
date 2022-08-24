package com.cloud.order.rocketmq.producer;

import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.order.mapper.CategoryMapper;
import com.cloud.order.rocketmq.producer.transaction.StrTransactionExecutor;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.domain.response.BaseResponse;
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
    private CategoryMapper categoryMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private StrTransactionExecutor strTransactionExecutor;


    /**
     * 发送普通消息
     */
    public BaseResponse sendMessage(String msg) {
        return cloudMQTemplate.send(PlatformCommonConstant.Topic.ORDER_SERVER_TOPIC, "EC_ORDER_SERVER", msg);
    }


    /**
     * 发送事务消息
     */
    public void sendTransactionMessage(String transactionMsg) {
        strTransactionExecutor.send(transactionMsg, "test arg");
    }

}
