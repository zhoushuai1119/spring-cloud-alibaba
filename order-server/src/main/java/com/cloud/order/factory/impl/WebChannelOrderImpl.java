package com.cloud.order.factory.impl;

import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.enums.OrderWayEnum;
import com.cloud.order.factory.AbstractChannelOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: WEB下单实现类
 * @author: zhou shuai
 * @date: 2022/8/12 14:57
 * @version: v1
 */
@Slf4j
@Service
public class WebChannelOrderImpl extends AbstractChannelOrder {

    @Override
    public Boolean createOrder(OrderParamDTO orderParamDTO) {
        log.info("使用web下单");
        orderParamDTO.setRemark("使用web下单");
        return createBaseOrder(orderParamDTO);
    }


    @Override
    public OrderWayEnum orderWay() {
        return OrderWayEnum.WEB;
    }

}
