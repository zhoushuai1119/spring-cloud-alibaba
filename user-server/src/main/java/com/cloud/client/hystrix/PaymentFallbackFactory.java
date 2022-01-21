package com.cloud.client.hystrix;

import com.cloud.client.PaymentClient;
import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.utils.FeignUtils;
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
public class PaymentFallbackFactory implements FallbackFactory<PaymentClient> {

    @Override
    public PaymentClient create(Throwable throwable) {
        log.error("fallback, cause:{}",throwable.getMessage());
        return new PaymentClient() {

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