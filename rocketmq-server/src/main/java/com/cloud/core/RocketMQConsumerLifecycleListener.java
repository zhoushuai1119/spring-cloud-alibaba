package com.cloud.core;

public interface RocketMQConsumerLifecycleListener<T> {
    void prepareStart(final T consumer);
}
