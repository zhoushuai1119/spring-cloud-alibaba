package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.dao.ProductMapper;
import com.cloud.entity.Product;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.rocketmq.producer.transaction.ProductTransactionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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
        cloudMQTemplate.send(CommonConstant.topic.PRODUCT_SERVER_TOPIC, "EC_PRODUCT_SERVER", "productMessage");
    }

    public void sendTransactionMessage() {
        Product product = productMapper.selectById("1");
        tokenUserTransactionExecutor.send(product, product.getId());
    }


}
