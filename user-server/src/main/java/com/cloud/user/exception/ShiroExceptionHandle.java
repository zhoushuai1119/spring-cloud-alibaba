package com.cloud.user.exception;


import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 统一异常处理类
 * @author: zhou shuai
 * @date: 2022/5/8 12:25
 * @version: v1
 */
@RestControllerAdvice
@Slf4j
public class ShiroExceptionHandle {

    @ExceptionHandler(UnauthorizedException.class)
    public BaseResponse handleUnauthorizedException(Exception ex) {
        log.error("权限验证失败:{}",ex.getMessage());
        return BaseResponse.createFailResult(
                new BaseException(UserErrorCodeEnum.PERMISSION_DENIED_ERROR,ex.getMessage()));
    }


    @ExceptionHandler(AuthorizationException.class)
    public BaseResponse handleAuthorizationException(Exception ex) {
        log.error("权限验证失败:{}",ex.getMessage());
        return BaseResponse.createFailResult(
                new BaseException(UserErrorCodeEnum.PERMISSION_DENIED_ERROR,ex.getMessage()));
    }

}
