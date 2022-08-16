package com.cloud.order.client.hystrix;

import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.utils.FeignUtils;
import com.cloud.order.client.ProductClient;
import com.cloud.order.domain.dto.PurchaseProductDTO;
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
public class ProductFallbackFactory implements FallbackFactory<ProductClient> {

    @Override
    public ProductClient create(Throwable throwable) {
        log.error("fallback, cause:{}", throwable.getMessage());
        return new ProductClient() {

            @Override
            public BaseResponse purchaseProduct(PurchaseProductDTO purchaseProductDTO) {
                BusinessException businessException = FeignUtils.decodeFeignException("purchaseProduct", throwable);
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
