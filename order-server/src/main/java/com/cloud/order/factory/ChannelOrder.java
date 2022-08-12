package com.cloud.order.factory;

import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.enums.OrderWayEnum;

/**
 * 订单顶级接口
 *
 * @author zhou shuai
 * @date 2020/11/15 12:13:13
 */
public interface ChannelOrder {

    /**
     * 创建订单接口
     *
     * @param orderParamDTO 订单信息
     * @return Boolean
     */
    Boolean createOrder(OrderParamDTO orderParamDTO);

    /**
     * 订单途径
     *
     * @return OrderWayEnum
     */
    OrderWayEnum orderWay();

}
