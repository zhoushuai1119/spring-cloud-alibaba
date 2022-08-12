package com.cloud.order.factory;


import com.cloud.order.domain.dto.OrderDetailDTO;
import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.domain.entity.OrderDetail;
import com.cloud.order.domain.entity.OrderProductDetail;
import com.cloud.order.service.OrderDetailService;
import com.cloud.order.service.OrderProductDetailService;
import com.cloud.platform.web.utils.CommonUtil;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * 订单下单接口父类
 *
 * @author zhou shuai
 * @date 2020/11/15 12:13:13
 */
@Slf4j
public abstract class AbstractChannelOrder implements ChannelOrder {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderProductDetailService orderProductDetailService;

    @Autowired
    private Mapper mapper;

    /**
     * 下单基础接口
     *
     * @param orderParamDTO 订单信息
     * @return true/false
     */
    public Boolean createBaseOrder(OrderParamDTO orderParamDTO) {

        //下单商品明细
        List<OrderDetailDTO> orderDetailList = orderParamDTO.getOrderDetailList();

        // 获取订单总金额
        BigDecimal orderAmount = getOrderAmount(orderDetailList);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderWay(orderParamDTO.getOrderWay());
        orderDetail.setOrderAmount(orderAmount);
        orderDetail.setRemark(orderParamDTO.getRemark());
        orderDetail.setUserId(1L);
        //保存订单
        orderDetailService.save(orderDetail);

        //保存下单商品明细
        orderDetailList.forEach(orderDetailDTO -> {
            orderDetailDTO.setOrderId(orderDetail.getId());
            OrderProductDetail productDetail = mapper.map(orderDetailDTO, OrderProductDetail.class);
            orderProductDetailService.save(productDetail);
        });
        return TRUE;
    }

    /**
     * 计算订单总金额
     *
     * @param orderDetailList
     * @return
     */
    private BigDecimal getOrderAmount(List<OrderDetailDTO> orderDetailList) {
        BigDecimal orderTotalAmount = BigDecimal.ZERO;
        if (CommonUtil.isNotEmpty(orderDetailList)) {
            for (OrderDetailDTO orderDetailDTO : orderDetailList) {
                BigDecimal orderAmount = orderDetailDTO.getProductPrice().multiply(BigDecimal.valueOf(orderDetailDTO.getProductQuantity()));
                orderTotalAmount = orderTotalAmount.add(orderAmount);
            }
        }
        return orderTotalAmount;
    }

}
