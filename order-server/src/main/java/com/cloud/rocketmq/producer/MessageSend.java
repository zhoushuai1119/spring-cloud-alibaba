package com.cloud.rocketmq.producer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.constants.CommonConstant;
import com.cloud.dao.CategoryMapper;
import com.cloud.entity.Category;
import com.cloud.mq.base.core.CloudMQTemplate;
import com.cloud.platform.common.constants.PlatformCommonConstant;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.rocketmq.producer.transaction.CategoryTransactionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
    private CloudMQTemplate cloudMQTemplate;

    @Autowired
    private CategoryTransactionExecutor categoryTransactionExecutor;


    /**
     * 发送普通消息
     */
    public BaseResponse sendMessage() {
        Category category = categoryMapper.selectById("1");
        return cloudMQTemplate.send(PlatformCommonConstant.Topic.ORDER_SERVER_TOPIC, "EC_ORDER_SERVER", category);
    }


    /**
     * 发送事务消息
     */
    public void sendTransactionMessage() {
        Category category = categoryMapper.selectById("1");
        if (Objects.nonNull(category)) {
            categoryTransactionExecutor.send(category, category.getCategoryId());
        }
    }


}
