package com.cloud.order.rocketmq.producer.transaction;

import com.cloud.order.domain.entity.Category;
import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.rocketmq.annotation.TansactionTopic;
import com.cloud.platform.rocketmq.core.BaseTransactionExecutor;
import com.cloud.platform.rocketmq.core.RocketMQTransactionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/14 15:34
 * @version: v1
 */
@Slf4j
@TansactionTopic(topic = PlatformCommonConstant.Topic.ORDER_SERVER_TOPIC_TRANSACTION, eventCode = "EC_ORDER_SERVER_TRANSACTION")
public class CategoryTransactionExecutor extends BaseTransactionExecutor<Category, Object> {

    public CategoryTransactionExecutor(RocketMQTransactionTemplate rocketMQTransactionTemplate) {
        super(rocketMQTransactionTemplate);
    }

    @Override
    public LocalTransactionState executeTransaction(CloudMessage<Category> message, Object arg) {
        log.info("事务消息执行本地事务*********");
        log.info("事务ID:{},消息内容{},参数:{}", message.getTransactionId(), message.getPayload(), arg);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(CloudMessage<Category> message) {
        log.info("事务消息回来查本地事务*********");
        log.info("事务ID:{},消息内容{}", message.getTransactionId(), message.getPayload());
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

}