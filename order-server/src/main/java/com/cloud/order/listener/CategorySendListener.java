package com.cloud.order.listener;

import com.cloud.order.domain.entity.Category;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * @author zhoushuai
 * @date 2020-05-20 14:20
 */
@Slf4j
@Component
public class CategorySendListener {

    @TransactionalEventListener
//    @EventListener
    public void sendMq(Category category) {
        log.info("TransactionalEventListener******");
        log.info(JsonUtil.toString(category));
    }

}
