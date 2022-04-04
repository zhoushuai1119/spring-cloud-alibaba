package com.cloud.rocketmq.producer.transaction;

import com.cloud.entity.TokenUser;
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
@TansactionTopic(topic = PlatformCommonConstant.Topic.USER_SERVER_TOPIC_TRANSACTION, eventCode = "EC_USER_SERVER_TRANSACTION")
public class TokenUserTransactionExecutor extends BaseTransactionExecutor<TokenUser, Object> {

    public TokenUserTransactionExecutor(RocketMQTransactionTemplate rocketMQTransactionTemplate) {
        super(rocketMQTransactionTemplate);
    }

    @Override
    public LocalTransactionState executeTransaction(CloudMessage<TokenUser> message, Object arg) {
        log.info("事务消息执行本地事务*********");
        log.info("事务ID:{},消息内容{},参数:{}", message.getTransactionId(), message.getPayload(), arg);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(CloudMessage<TokenUser> message) {
        log.info("事务消息回来查本地事务*********");
        log.info("事务ID:{},消息内容{}", message.getTransactionId(), message.getPayload());
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

}
