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
     * feign 调用失败
     */
    FEIGN_CLIENT_ERROR("200001", "feign调用失败"),

    /**
     * 非法请求
     */
    NOT_LEGAL_ERROR("200002", "非法请求"),

    /**
     * 订单库存不足
     */
    INSUFFICIENT_INVENTORY_ERROR("200003", "订单库存不足"),

    /**
     * 您不是该任务的审批人
     */
    NOT_TASK_APPROVAL_PERSON("200004", "您不是该任务的审批人"),

    /**
     * 登录失败,该用户不存在
     */
    LOGIN_FAIL_USER_NOT_EXIST("200005", "登录失败,该用户不存在"),

    /**
     * 登录失败,密码错误
     */
    LOGIN_FAIL_PASSWORD_ERROR("200006", "登录失败,密码错误"),

    /**
     * 登录失败,验证码已过期
     */
    LOGIN_FAIL_VALIDATE_CODE_EXPIRE("200007", "登录失败,验证码已过期"),

    /**
     * 登录失败,验证码错误
     */
    LOGIN_FAIL_VALIDATE_CODE_ERROR("200008", "登录失败,验证码错误");


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
