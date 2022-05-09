package com.cloud.product.rocketmq.producer;

import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.product.domain.entity.Product;
import com.cloud.product.mapper.ProductMapper;
import com.cloud.product.rocketmq.producer.transaction.ProductTransactionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 17:08
 * @version: v1
 */
@Slf4j
@Component
public class MessageSend {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private ProductTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage() {
        cloudMQTemplate.send(PlatformCommonConstant.Topic.PRODUCT_SERVER_TOPIC, "EC_PRODUCT_SERVER", "productMessage");
    }

    public void sendTransactionMessage() {
        Product product = productMapper.selectById("1");
        tokenUserTransactionExecutor.send(product, product.getId());
    }


}
