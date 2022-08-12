package com.cloud.product.rocketmq.consumer;

import cn.hutool.json.JSONUtil;
import com.cloud.common.constants.CommonConstant;
import com.cloud.mq.base.dto.CloudMessage;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.platform.rocketmq.annotation.ConsumeTopic;
import com.cloud.platform.rocketmq.core.TopicListener;
import com.cloud.platform.web.utils.CommonUtil;
import com.cloud.product.domain.dto.OrderProductDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description: 订单扣减库存mq 监听
 * @author: zhou shuai
 * @date: 2022/8/12 16:28
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = PlatformCommonConstant.Topic.PRODUCT_SERVER_TOPIC, eventCode = CommonConstant.EventCode.ORDER_PRODUCT_CODE, log = true)
public class OrderProductConsumer implements TopicListener<String> {

    @Override
    public void onMessage(CloudMessage<String> message) throws Exception {
        String orderProductMsg = message.getPayload();
        log.info("OrderProductConsumer接收到消息:{}", orderProductMsg);
        if (CommonUtil.isEmpty(orderProductMsg)) {
            log.warn("orderProductMsg is empty");
            return;
        }
        List<OrderProductDTO> orderProductList = JSONUtil.toList(orderProductMsg, OrderProductDTO.class);
        log.info("orderProductList:{}", JsonUtil.toString(orderProductList));
    }

}
