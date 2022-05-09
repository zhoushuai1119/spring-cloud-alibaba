package com.cloud.payment.proxy;

import com.cloud.payment.client.OrderClient;
import com.cloud.common.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 19:17
 * @version: v1
 */
@Component
@Slf4j
public class OrderProxy {

    @Autowired
    private OrderClient orderClient;

    /**
     * 保存用户
     */
    public void saveUser() {
        BusinessUtils.checkBaseRespose(orderClient.saveUser());
    }

}
