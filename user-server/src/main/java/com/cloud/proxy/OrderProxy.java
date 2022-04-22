package com.cloud.proxy;

import com.cloud.client.OrderClient;
import com.cloud.common.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/21 11:18
 * @version: v1
 */
@Component
@Slf4j
public class OrderProxy {

    @Autowired
    private OrderClient orderClient;

    /**
     * 更新categiry
     */
    public void updateCategory(String categoryId) {
        BusinessUtils.checkBaseRespose(orderClient.updateCategory(categoryId));
    }

}
