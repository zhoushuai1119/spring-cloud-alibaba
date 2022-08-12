package com.cloud.order.listener;

import com.cloud.common.constants.CommonConstant;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 * @author zhoushuai
 * @date 2020-05-20 14:20
 */
@Slf4j
@Component
public class ProductSendListener {

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    /**
     * 下单扣减库存发送mq
     * @param orderDetailList
     */
    @TransactionalEventListener
    public void productSendMq(OrderParamDTO createOrderParam) {
        log.info("ProductSendListener, orderDetailList:{}", JsonUtil.toString(createOrderParam.getOrderDetailList()));
        cloudMQTemplate.send(PlatformCommonConstant.Topic.PRODUCT_SERVER_TOPIC, CommonConstant.EventCode.ORDER_PRODUCT_CODE, JsonUtil.toString(createOrderParam.getOrderDetailList()));
    }

}
