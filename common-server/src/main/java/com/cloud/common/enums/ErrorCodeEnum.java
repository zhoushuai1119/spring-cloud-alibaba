package com.cloud.common.enums;

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
public enum ErrorCodeEnum implements BaseExceptionCode {

    /**
     * 成功
     */
    SUCCESS("000000", "success"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("100000", "system error"),

    /**
     * 参数错误
     */
    PARAM_ERROR("100001", "参数错误"),

    /**
     * Json解析错误
     */
    JSON_PARSER_ERROR("100002", "json parse error"),

    /**
     * feign 调用失败
     */
    FEIGN_CLIENT_ERROR("100003", "feign调用失败"),

    /**
     * 非法请求
     */
    NOT_LEGAL_ERROR("100004", "非法请求"),

    /**
     * 订单库存不足
     */
    INSUFFICIENT_INVENTORY_ERROR("100005", "订单库存不足"),

    /**
     * 您不是该任务的审批人
     */
    NOT_TASK_APPROVAL_PERSON("100006", "您不是该任务的审批人");

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
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

    @Override
    public String getErrorTips() {
        return message;
    }
}
