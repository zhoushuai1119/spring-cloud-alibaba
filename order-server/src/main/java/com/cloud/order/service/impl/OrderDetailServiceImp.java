package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.config.EnvConfig;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.utils.RedisLockKeyUtil;
import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.domain.dto.PurchaseProductDTO;
import com.cloud.order.domain.entity.OrderDetail;
import com.cloud.order.factory.ChannelOrder;
import com.cloud.order.factory.OrderFactory;
import com.cloud.order.mapper.OrderDetailMapper;
import com.cloud.order.proxy.ProductProxy;
import com.cloud.order.service.OrderDetailService;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.github.dozermapper.core.Mapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
@Service
public class OrderDetailServiceImp extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private OrderFactory orderFactory;

    @Autowired
    private ProductProxy productProxy;

    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private Mapper mapper;


    /**
     * 创建订单(分布式事务seata)
     *
     * @param createOrderParamDTO
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public BaseResponse createOrder(OrderParamDTO createOrderParamDTO) {
        //redis分布式锁的lockKey
        String lockKey = RedisLockKeyUtil.getLockKey(CommonConstant.SystemCode.ORDER_SERVER, envConfig.getEnv(),
                CommonConstant.RedisLockKey.CREATE_ORDER_KEY,
                String.valueOf(createOrderParamDTO.getOrderWay()));
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            //获取订单实例
            ChannelOrder channelOrder = orderFactory.chooseOrderObj(createOrderParamDTO.getOrderWay());
            channelOrder.createOrder(createOrderParamDTO);

            String orderId = createOrderParamDTO.getOrderDetailList().get(0).getOrderId();

            PurchaseProductDTO purchaseProductDTO = PurchaseProductDTO.builder()
                    .orderId(orderId)
                    .purchaseProductList(createOrderParamDTO.getOrderDetailList())
                    .build();

            //调用商品服务扣减库存
            productProxy.purchaseProduct(purchaseProductDTO);
        } finally {
            lock.unlock();
        }
        return BaseResponse.createSuccessResult(null);
    }

}
