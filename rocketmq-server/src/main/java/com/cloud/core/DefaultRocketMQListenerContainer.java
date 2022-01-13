package com.cloud.core;


import com.cloud.enums.ConsumeMode;
import com.cloud.enums.SelectorType;
import com.cloud.util.MqMessageUtils;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DefaultRocketMQListenerContainer implements InitializingBean, RocketMQListenerContainer {

    @Setter
    @Getter
    private long suspendCurrentQueueTimeMillis = 1000;

    /**
     * Message consume retry strategy<br> -1,no retry,put into DLQ directly<br> 0,broker control retry frequency<br>
     * >0,client control retry frequency
     */
    @Setter
    @Getter
    private int delayLevelWhenNextConsume = 0;

    @Setter
    @Getter
    private String consumerGroup;

    @Setter
    @Getter
    private String nameServer;
    /**
     * key: topic, value: tags
     */
    @Setter
    @Getter
    private Map<String, String> subscription;

    @Setter
    @Getter
    private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;

    @Setter
    @Getter
    private SelectorType selectorType = SelectorType.TAG;

    @Setter
    @Getter
    private MessageModel messageModel = MessageModel.CLUSTERING;

    @Setter
    @Getter
    private int consumeThreadMax = 64;

    @Setter
    @Getter
    private int consumeThreadMin = 20;

    @Getter
    @Setter
    private String charset = "UTF-8";

    @Setter
    @Getter
    private boolean started;

    @Setter
    private MonsterMQListener rocketMQListener;

    @Setter
    private RocketMQTemplate rocketMQTemplate;

    @Setter
    private String instanceId;

    @Setter
    private RPCHook rpcHook;

    private DefaultMQPushConsumer consumer;

    private Class messageType;

    @Setter
    @Getter
    private int consumeMessageBatchMaxSize;

    @Setter
    @Getter
    private MeterRegistry threadPoolMeterRegistry;

    public void setupMessageListener(MonsterMQListener rocketMQListener) {
        this.rocketMQListener = rocketMQListener;
    }

    @Override
    public void destroy() {
        this.setStarted(false);
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this);
    }

    public synchronized void start() throws MQClientException {

        if (this.isStarted()) {
            throw new IllegalStateException("container already started. " + this);
        }

        initRocketMQPushConsumer();

        // parse message type
        this.messageType = MqMessageUtils.getMessageTypeByMonsterMQListener(rocketMQListener);
        log.debug("msgType: {}", messageType.getName());

        consumer.start();
        this.setStarted(true);

        log.info("started container: {}", this);
    }

    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @SuppressWarnings("unchecked")
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            log.debug("concurrently received msgs size:{}", msgs.size());
            for (MessageExt messageExt : msgs) {
                log.debug("concurrently received msg: {}", messageExt);
                try {
                    consumeInner(messageExt);
                } catch (Exception e) {
                    log.warn("consume message failed. messageExt:{}", messageExt, e);
                    context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    public class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @SuppressWarnings("unchecked")
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            log.debug("orderly received msgs size:{}", msgs.size());
            for (MessageExt messageExt : msgs) {
                log.debug("orderly received msg: {}", messageExt);
                try {
                    consumeInner(messageExt);
                } catch (Exception e) {
                    log.warn("consume message failed. messageExt:{}", messageExt, e);
                    context.setSuspendCurrentQueueTimeMillis(suspendCurrentQueueTimeMillis);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    private void consumeInner(MessageExt messageExt) throws Exception {
        long now = System.currentTimeMillis();
        try {
            rocketMQListener.onMessage(MqMessageUtils.doConvertMessageExtByClass(messageExt, messageType, true));
        } catch (Exception e) {
            throw e;
        }
        long costTime = System.currentTimeMillis() - now;
        log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    @Override
    public String toString() {
        return "DefaultRocketMQListenerContainer{" +
                "consumerGroup='" + consumerGroup + '\'' +
                ", nameServer='" + nameServer + '\'' +
                ", subscription='" + subscription + '\'' +
                ", consumeMode=" + consumeMode +
                ", selectorType=" + selectorType +
                ", messageModel=" + messageModel +
                ", consumeTreadMax=" + consumeThreadMax +
                ", consumeTreadMin=" + consumeThreadMin +
                '}';
    }


    private void initRocketMQPushConsumer() throws MQClientException {

        Assert.notNull(rocketMQListener, "Property 'rocketMQListener' is required");
        Assert.notNull(consumerGroup, "Property 'consumerGroup' is required");
        Assert.notNull(nameServer, "Property 'nameServer' is required");
        Assert.notNull(subscription, "Property 'subscription' is required");

        consumer = new DefaultMQPushConsumer(null, consumerGroup, rpcHook);
        consumer.setNamesrvAddr(nameServer);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumeThreadMin = Math.min(consumeThreadMin, consumeThreadMax);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setMessageModel(messageModel);

        //分配策略 改为环形分配（使得队列消费范围较为均匀）
        consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());
        //设置最大批量消费数量
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        switch (selectorType) {
            case TAG:
                for (Map.Entry<String, String> topicTags : subscription.entrySet()) {
                    consumer.subscribe(topicTags.getKey(), topicTags.getValue());
                }
                break;
            case SQL92:
                for (Map.Entry<String, String> topicTags : subscription.entrySet()) {
                    consumer.subscribe(topicTags.getKey(), MessageSelector.bySql(topicTags.getValue()));
                }
                break;
            default:
                throw new IllegalArgumentException("Property 'selectorType' was wrong.");
        }

        //从上下文可以保证，每一个[top evenCode]消息，都有一个[top evenCode] listener 处理相应的逻辑

        switch (consumeMode) {
            case ORDERLY:
                consumer.setMessageListener(new DefaultMessageListenerOrderly());
                break;
            case CONCURRENTLY:
                consumer.setMessageListener(new DefaultMessageListenerConcurrently());
                break;
            default:
                throw new IllegalArgumentException("Property 'consumeMode' was wrong.");
        }

    }

}
