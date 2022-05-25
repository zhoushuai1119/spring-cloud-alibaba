package com.cloud.user.client;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.user.client.hystrix.OrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 14:32
 * @version: v1
 */
@FeignClient(name = OrderClient.SERVER_NAME,
             path = OrderClient.SERVER_NAME,
             fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {

    String SERVER_NAME = "order-server";

    @PostMapping(value = "/category/updateCategory/{categoryId}")
    BaseResponse updateCategory(@PathVariable("categoryId") String categoryId);

}
