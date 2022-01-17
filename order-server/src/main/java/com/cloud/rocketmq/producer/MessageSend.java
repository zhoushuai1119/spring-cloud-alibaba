package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.order.Category;
import com.cloud.core.MonsterMQTemplate;
import com.cloud.dao.CategoryMapper;
import com.cloud.rocketmq.producer.transaction.CategoryTransactionExecutor;
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
    private CategoryMapper categoryMapper;

    @Autowired
    private MonsterMQTemplate monsterMQTemplate;

    @Autowired
    private CategoryTransactionExecutor categoryTransactionExecutor;


    /**
     * 发送普通消息
     */
    public BaseResponse sendMessage(){
        List<Category> categoryList = categoryMapper.selectList(new QueryWrapper<>());
        return monsterMQTemplate.send(CommonConstant.topic.ORDER_SERVER_TOPIC,"EC_ORDER_SERVER",categoryList);
    }


    /**
     * 发送事务消息
     */
    public void sendTransactionMessage(){
        List<Category> categoryList = categoryMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(categoryList)) {
            categoryList.forEach(category -> {
                categoryTransactionExecutor.send(category,category.getCategoryId());
            });
        }
    }


}
