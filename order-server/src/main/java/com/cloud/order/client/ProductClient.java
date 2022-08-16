package com.cloud.order.client;

import com.cloud.order.client.hystrix.ProductFallbackFactory;
import com.cloud.order.domain.dto.PurchaseProductDTO;
import com.cloud.platform.common.domain.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 14:32
 * @version: v1
 */
@FeignClient(name = ProductClient.SERVER_NAME,
        path = ProductClient.SERVER_NAME,
        fallbackFactory = ProductFallbackFactory.class)
public interface ProductClient {

    String SERVER_NAME = "product-server";

    /**
     * 购买产品扣减库存
     *
     * @param purchaseProductDTO
     * @return
     */
    @PostMapping(value = "/product/purchase")
    BaseResponse purchaseProduct(@RequestBody PurchaseProductDTO purchaseProductDTO);

}
