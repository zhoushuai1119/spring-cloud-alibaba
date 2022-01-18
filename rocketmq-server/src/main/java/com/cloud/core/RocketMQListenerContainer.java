package com.cloud.core;

import org.springframework.beans.factory.DisposableBean;

public interface RocketMQListenerContainer extends DisposableBean {

    void setupMessageListener(CloudMQListener<?> messageListener);
}
