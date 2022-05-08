package com.cloud.order.common.exception;

import com.cloud.platform.common.exception.BaseExceptionCode;
import lombok.Getter;

/**
 * 错误信息enum
 *
 * @Author 施文
 * @Date 2019/06/23
 * @Time 13:46:19
 */
@Getter
public enum OrderErrorCodeEnum implements BaseExceptionCode {

    /**
     * 订单库存不足
     */
    INSUFFICIENT_INVENTORY_ERROR("400001", "订单库存不足");


    private String code;
    private String message;

    OrderErrorCodeEnum(String code, String message) {
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
