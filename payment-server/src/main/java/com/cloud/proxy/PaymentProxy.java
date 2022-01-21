package com.cloud.proxy;

import com.cloud.client.OrderClient;
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
public class PaymentProxy {

    @Autowired
    private OrderClient orderClient;

    /**
     * 保存用户
     */
    public void saveUser() {
        BusinessUtils.checkBaseRespose(orderClient.saveUser());
    }

}