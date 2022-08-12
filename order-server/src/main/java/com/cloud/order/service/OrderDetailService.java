package com.cloud.order.service;

import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.domain.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.platform.common.domain.response.BaseResponse;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 创建订单
     *
     * @param createOrderParamDTO
     * @return
     */
    BaseResponse createOrder(OrderParamDTO createOrderParamDTO);

}
