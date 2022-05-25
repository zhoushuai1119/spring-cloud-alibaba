package com.cloud.order.client.hystrix;

import com.cloud.order.client.UserClient;
import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.utils.FeignUtils;
import com.cloud.order.domain.dto.UserRegisterDTO;
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
            public BaseResponse userRegister(UserRegisterDTO userRegisterDTO) {
                BusinessException businessException = FeignUtils.decodeFeignException("userRegister",throwable);
                BaseResponse response = new BaseResponse();
                response.setSuccess(false);
                response.setErrorCode(businessException.getErrorCode());
                response.setErrorTips(businessException.getErrorTips());
                response.setErrorMessage(businessException.getMessage());
                return response;
            }
        };
    }

}
