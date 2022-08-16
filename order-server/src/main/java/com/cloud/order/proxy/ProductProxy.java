package com.cloud.order.proxy;

import com.cloud.common.utils.BusinessUtils;
import com.cloud.order.client.ProductClient;
import com.cloud.order.domain.dto.PurchaseProductDTO;
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
     * 购买产品扣减库存
     *
     * @param purchaseProductDTO
     * @return
     */
    public void purchaseProduct(PurchaseProductDTO purchaseProductDTO) {
        BusinessUtils.checkBaseRespose(productClient.purchaseProduct(purchaseProductDTO));
    }

}
