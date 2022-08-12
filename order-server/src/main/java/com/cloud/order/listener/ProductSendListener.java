package com.cloud.order.listener;

import com.cloud.common.constants.CommonConstant;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.order.domain.dto.OrderDetailDTO;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;


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
    @EventListener
    public void productSendMq(List<OrderDetailDTO> orderDetailList) {
        log.info("ProductSendListener, orderDetailList:{}", JsonUtil.toString(orderDetailList));
        cloudMQTemplate.send(PlatformCommonConstant.Topic.PRODUCT_SERVER_TOPIC, CommonConstant.EventCode.ORDER_PRODUCT_CODE, JsonUtil.toString(orderDetailList));
    }

}
