package com.cloud.core;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

public interface RocketMQPushConsumerLifecycleListener extends RocketMQConsumerLifecycleListener<DefaultMQPushConsumer> {
}
