package com.cloud.user.client;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.user.client.hystrix.ProductFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

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
     * 保存产品信息
     * @return
     */
    @PostMapping(value = "/product/save")
    BaseResponse saveProduct();

    /**
     * seata 测试
     * @return
     */
    @PostMapping(value = "/product/seata/test")
    BaseResponse seataTest();

}
