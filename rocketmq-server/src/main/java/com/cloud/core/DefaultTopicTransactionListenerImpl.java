package com.cloud.core;


import com.cloud.annotation.TansactionTopic;
import com.cloud.dto.CloudMessage;
import com.cloud.util.MqMessageUtils;
import com.fasterxml.jackson.databind.JavaType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 现类用于接收 mq原始 封装
 */
@Slf4j
public class DefaultTopicTransactionListenerImpl implements TransactionListener {

    /**
     * key 为 topicAndEventCode
     * ConsumeTransTopicInfo 有对应的消费逻辑 listener
     */
    protected static Map<String, TransactionTopicMsgInfo> topicConsumerMap = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private String charset = "UTF-8";

    /**
     * 执行本地事务
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        String key = message.getTopic() + ":" + message.getTags();
        TransactionTopicMsgInfo transTopicInfo = topicConsumerMap.get(key);
        if (Objects.nonNull(transTopicInfo)) {
            CloudMessage<Object> messageConvert = null;
            try {
                messageConvert = MqMessageUtils.doConvertMessageByClass(message, transTopicInfo.getMessageType());
            } catch (Exception e) {
                return LocalTransactionState.UNKNOW;
            }

            LocalTransactionState transactionStateEnum = transTopicInfo.getTopicTransactionListener()
                    .executeTransaction(messageConvert, arg);
            return transactionStateEnum;
        } else {
            log.warn("cant find local transaction listener key = {}", key);
        }
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 回查事务
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt message) {
        String key = message.getTopic() + ":" + message.getTags();
        TransactionTopicMsgInfo transTopicInfo = topicConsumerMap.get(key);
        if (Objects.nonNull(transTopicInfo)) {
            CloudMessage<Object> messageConvert = null;
            try {
                messageConvert = MqMessageUtils.doConvertMessageByClass(message, transTopicInfo.getMessageType());
            } catch (Exception e) {
                return LocalTransactionState.UNKNOW;
            }
            LocalTransactionState transactionStateEnum = transTopicInfo.getTopicTransactionListener()
                    .checkLocalTransaction(messageConvert);
            return transactionStateEnum;
        } else {
            log.warn("cant find check transaction listener key = {}", key);
        }
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 注册监听到map内
     *
     * @Author 马腾飞
     * @Date 2019/12/11 15:58
     */
    protected static void registerListener(TopicTransactionListener topicListener) throws Exception {
        //listener 注解中的 topicAndEventCode 得到 topicInfo
        TransactionTopicMsgInfo topicInfo = getConsumeTopicAndEventCode(topicListener);
        if (topicInfo != null) {
            String topicAndEventCode = topicInfo.getTopic() + ":" + topicInfo.getEventCode();
            TransactionTopicMsgInfo existed = topicConsumerMap.get(topicAndEventCode);
            if (existed != null) {
                throw new Exception("duplicated topic eventCode listener: "
                        + existed.getTopicTransactionListener().getClass().getName() + " and " + topicListener.getClass().getName());
            } else {
                topicInfo.setTopicTransactionListener(topicListener);
                //设置消息转化类型
                topicInfo.setMessageType(MqMessageUtils.getMessageJavaType2TopicTransaction(topicInfo.getTopicTransactionListener()));
                topicConsumerMap.put(topicAndEventCode, topicInfo);
            }
        }
    }

    /**
     * 从ConsumeTopic注解中获取topic和eventCode
     *
     * @param consumeTopic
     * @return
     */
    private static TransactionTopicMsgInfo getConsumeTopicAndEventCode(TopicTransactionListener consumeTopic) {
        Annotation annotation = null;
        Class clazz = consumeTopic.getClass();
        if (AopUtils.isAopProxy(consumeTopic)) {
            //@Transactional会增加AOP代理，查找真实的类对象
            clazz = ((Advised) consumeTopic).getTargetSource().getTargetClass();
        }
        annotation = clazz.getAnnotation(TansactionTopic.class);

        if (annotation == null) {
            log.error("transaction topic listener {} has no consumetopic annotation", consumeTopic.getClass().getName());
        } else {
            TransactionTopicMsgInfo topicInfo = null;
            Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotation);
            if (attrs != null && attrs.containsKey("topic") && attrs.containsKey("eventCode")) {
                topicInfo = new TransactionTopicMsgInfo();
                topicInfo.setTopic(attrs.get("topic").toString());
                topicInfo.setEventCode(attrs.get("eventCode").toString());
            }
            return topicInfo;
        }
        return null;
    }

    /**
     * topic事务信息
     */
    @Data
    private static final class TransactionTopicMsgInfo {
        /**
         * topic
         */
        private String topic;

        /**
         * eventCode
         */
        private String eventCode;

        /**
         * listener
         */
        private TopicTransactionListener topicTransactionListener;

        /**
         * 消息的json转换类型
         */
        private JavaType messageType;
    }

}
