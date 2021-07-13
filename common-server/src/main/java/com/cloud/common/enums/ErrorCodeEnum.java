package com.cloud.common.enums;

import com.cloud.common.beans.exception.BaseExceptionCode;
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
     * Dubbo远程调用失败
     */
    RPC_ERROR("100002","Dubbo远程调用失败"),

    /**
     * Json解析错误
     */
    JSON_PARSER_ERROR("100003", "json parse error")

    ;

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
