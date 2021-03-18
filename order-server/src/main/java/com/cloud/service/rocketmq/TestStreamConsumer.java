package com.cloud.service.rocketmq;

import com.cloud.common.entity.order.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestStreamConsumer {

    /**
     * 我们消费的消息是 POJO 类型，所以我们需要添加 @Payload 注解，
     * 声明需要进行反序列化成 POJO 对象
     * @param message
     */
    @StreamListener(value = MySink.ERBADAGANG_INPUT)
    public void onMessage(@Payload Category message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message.getCategoryName());
    }

    @StreamListener(MySink.TREK_INPUT)
    public void onTrekMessage(@Payload String message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }


}

