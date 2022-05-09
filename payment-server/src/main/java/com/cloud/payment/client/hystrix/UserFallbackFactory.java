package com.cloud.payment.client.hystrix;

import com.cloud.payment.client.UserClient;
import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.utils.FeignUtils;
import com.cloud.platform.common.domain.response.BaseResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 14:35
 * @version: v1
 */
@Component
@Slf4j
public class UserFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        log.error("fallback, cause:{}",throwable.getMessage());
        return new UserClient() {

            @Override
            public BaseResponse saveUser() {
                BusinessException businessException = FeignUtils.decodeFeignException("saveUser",throwable);
                BaseResponse response = new BaseResponse();
                response.setSuccess(false);
                response.setErrorMessage(businessException.getMessage());
                response.setErrorCode(businessException.getErrorCode());
                return response;
            }
        };
    }

}
