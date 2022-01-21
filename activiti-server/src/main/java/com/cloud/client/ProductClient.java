package com.cloud.client;

import com.cloud.client.hystrix.ProductFallbackFactory;
import com.cloud.common.beans.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "saveUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse saveUser();

}
