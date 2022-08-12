package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.order.common.constants.OrderConstant;
import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.domain.entity.OrderDetail;
import com.cloud.order.factory.ChannelOrder;
import com.cloud.order.factory.OrderFactory;
import com.cloud.order.mapper.OrderDetailMapper;
import com.cloud.order.service.OrderDetailService;
import com.cloud.order.utils.RedisLockKeyUtil;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.github.dozermapper.core.Mapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private ApplicationEventPublisher publisher;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private Mapper mapper;


    /**
     * 创建订单
     *
     * @param createOrderParamDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrder(OrderParamDTO createOrderParamDTO) {
        String lockKey = RedisLockKeyUtil.getLockKey(createOrderParamDTO.getOrderWay(), env, OrderConstant.CreateOrderKey.CREATE_ORDER_KEY);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            // 选择创建订单策略
            ChannelOrder channelOrder = orderFactory.chooseOrderObj(createOrderParamDTO.getOrderWay());
            channelOrder.createOrder(createOrderParamDTO);

            //发送mq 扣减库存
            publisher.publishEvent(createOrderParamDTO);
        } finally {
            lock.unlock();
        }
        return BaseResponse.createSuccessResult(null);
    }

}
