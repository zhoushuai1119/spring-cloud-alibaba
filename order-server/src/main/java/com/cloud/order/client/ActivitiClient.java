package com.cloud.order.client;

import com.cloud.order.client.hystrix.ActivitiFallbackFactory;
import com.cloud.platform.common.domain.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 14:32
 * @version: v1
 */
@FeignClient(name = ActivitiClient.SERVER_NAME,
             path = ActivitiClient.SERVER_NAME,
             fallbackFactory = ActivitiFallbackFactory.class)
public interface ActivitiClient {

    String SERVER_NAME = "activiti-server";

    @PostMapping(value = "saveUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse saveUser();

}