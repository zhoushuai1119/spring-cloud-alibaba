package com.cloud.client;

import com.cloud.client.hystrix.PaymentFallbackFactory;
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
@FeignClient(name = PaymentClient.SERVER_NAME,
             path = PaymentClient.SERVER_NAME,
             fallbackFactory = PaymentFallbackFactory.class)
public interface PaymentClient {

    String SERVER_NAME = "payment-server";

    @PostMapping(value = "/user/saveUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse saveUser();

}
