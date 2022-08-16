package com.cloud.product.common.exception;

import com.cloud.platform.common.exception.BaseExceptionCode;
import lombok.Getter;

/**
 * 错误信息enum
 *
 * @Author zhou shuai
 * @Date 2019/06/23
 * @Time 13:46:19
 */
@Getter
public enum ProductErrorCodeEnum implements BaseExceptionCode {

    /**
     * 订单库存不足
     */
    PRODUCT_INVENTORY_INSUFFICIENT_ERROR("400001", "订单库存不足");


    private String code;
    private String message;

    ProductErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

}
