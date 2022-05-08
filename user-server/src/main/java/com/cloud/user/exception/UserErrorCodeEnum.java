package com.cloud.user.exception;

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
public enum UserErrorCodeEnum implements BaseExceptionCode {

    /**
     * 登录失败,该用户不存在
     */
    UNKNOWN_ACCOUNT_ERROR("300001", "登录失败,该账户不存在"),

    /**
     * 登录失败,密码错误 IncorrectCredentialsException
     */
    INCORRECT_CREDENTIALS_ERROR("300002", "登录失败,密码错误"),

    /**
     * 登录失败,验证码已过期
     */
    VALIDATE_CODE_EXPIRE_ERROR("300003", "登录失败,验证码已过期"),

    /**
     * 登录失败,验证码错误
     */
    VALIDATE_CODE_CHECK_ERROR("300004", "登录失败,验证码错误"),

    /**
     * 登录失败多次，账户锁定10分钟
     */
    EXCESSIVE_ATTEMPTS_EEROR("300005", " 登录失败多次，账户锁定10分钟"),

    /**
     * 未知错误
     */
    UNKNOWM_ERROR("300006", "未知错误"),

    /**
     * 权限不足,拒绝访问
     */
    PERMISSION_DENIED_ERROR("300007", "权限不足,拒绝访问"),

    /**
     * 未登录，请先登录
     */
    UN_LOGIN_ERROR("300008", "权限不足,拒绝访问");


    private String code;
    private String message;

    UserErrorCodeEnum(String code, String message) {
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
