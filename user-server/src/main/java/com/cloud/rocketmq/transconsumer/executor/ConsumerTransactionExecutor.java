package com.cloud.rocketmq.transconsumer.executor;

import com.cloud.annotation.TansactionTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.core.BaseTransactionExecutor;
import com.cloud.core.RocketMQTransactionTemplate;
import com.cloud.dto.MonsterMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/14 15:34
 * @version: v1
 */
@Slf4j
@TansactionTopic(topic = CommonConstant.topic.USER_SERVER_TOPIC, eventCode = "EC_USER_SERVER_TRANSACTION",log = true)
public class ConsumerTransactionExecutor extends BaseTransactionExecutor<String,Object> {


    public ConsumerTransactionExecutor(RocketMQTransactionTemplate rocketMQTransactionTemplate) {
        super(rocketMQTransactionTemplate);
    }

    @Override
    public LocalTransactionState executeTransaction(MonsterMessage<String> message, Object arg) {
        log.info("事务消息执行本地事务*********");
        log.info("收到消息{},参数:{}",message.getPayload(),arg);
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MonsterMessage<String> message) {
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

}
