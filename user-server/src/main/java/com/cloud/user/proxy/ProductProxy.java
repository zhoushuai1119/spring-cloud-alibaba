package com.cloud.user.proxy;

import com.cloud.common.utils.BusinessUtils;
import com.cloud.user.client.ProductClient;
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
public class ProductProxy {

    @Autowired
    private ProductClient productClient;

    /**
     * 保存用户
     */
    public void saveProduct() {
        BusinessUtils.checkBaseRespose(productClient.saveProduct());
    }

}
