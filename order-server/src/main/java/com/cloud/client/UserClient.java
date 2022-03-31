package com.cloud.client;

import com.cloud.client.hystrix.UserFallbackFactory;
import com.cloud.platform.common.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 14:32
 * @version: v1
 */
@FeignClient(name = UserClient.SERVER_NAME,
             path = UserClient.SERVER_NAME,
             fallbackFactory = UserFallbackFactory.class)
public interface UserClient {

    String SERVER_NAME = "user-server";

    @PostMapping(value = "saveUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse saveUser();

}
