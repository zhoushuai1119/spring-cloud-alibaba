package com.cloud.service.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    String ERBADAGANG_INPUT = "erbadagang-input";


    @Input(ERBADAGANG_INPUT)
    SubscribableChannel demo01Input();

}
