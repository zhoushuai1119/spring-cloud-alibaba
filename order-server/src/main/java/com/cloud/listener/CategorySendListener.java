package com.cloud.listener;

import com.cloud.common.entity.order.Category;
import com.cloud.rabbitmq.direct.DirectSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * @author nianshuaishuai
 * @date 2020-05-20 14:20
 */
@Slf4j
@Component
public class CategorySendListener {

    @Autowired
    private DirectSender directSender;


    @TransactionalEventListener
//    @EventListener
    public void sendMq(Category category) {
        System.out.println(category);
        directSender.send(category);
    }

}
