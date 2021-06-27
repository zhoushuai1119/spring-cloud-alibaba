package com.cloud.common;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.utils.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 20:04
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public BaseResponse<Object> handleControllerException(HttpServletRequest request, Throwable ex) {
        return ExceptionUtils.logAndResponse(request.getRequestURI(), ex);
    }

}
