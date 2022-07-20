package com.cloud.order.client;

import com.cloud.order.client.hystrix.UserFallbackFactory;
import com.cloud.order.domain.dto.UserRegisterDTO;
import com.cloud.platform.common.domain.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping(value = "/user/register")
    BaseResponse userRegister(@RequestBody UserRegisterDTO userRegisterDTO);

}
