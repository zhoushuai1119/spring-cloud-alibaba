package com.cloud.order.service.impl;

import com.cloud.order.domain.entity.OrderProductDetail;
import com.cloud.order.mapper.OrderProductDetailMapper;
import com.cloud.order.service.OrderProductDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单产品详情表 服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
@Service
public class OrderProductDetailServiceImp extends ServiceImpl<OrderProductDetailMapper, OrderProductDetail> implements OrderProductDetailService {

}
