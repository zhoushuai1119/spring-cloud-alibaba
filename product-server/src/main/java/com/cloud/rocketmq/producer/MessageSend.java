package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.product.Product;
import com.cloud.core.MonsterMQTemplate;
import com.cloud.dao.ProductMapper;
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
    private MonsterMQTemplate monsterMQTemplate;

    @Autowired
    private ProductTransactionExecutor tokenUserTransactionExecutor;


    public void sendMessage(){
        List<Product> productList = productMapper.selectList(new QueryWrapper<>());
        monsterMQTemplate.send(CommonConstant.topic.PRODUCT_SERVER_TOPIC,"EC_PRODUCT_SERVER",productList);
    }

    public void sendTransactionMessage(){
        List<Product> productList = productMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(productList)) {
            productList.forEach(product -> {
                tokenUserTransactionExecutor.send(product,product.getId());
            });
        }
    }


}
