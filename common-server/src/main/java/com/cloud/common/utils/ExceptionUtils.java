package com.cloud.common.utils;

import com.cloud.common.beans.exception.BaseException;
import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.enums.ErrorCodeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @Description: 全局异常处理
 * @Author: ZhouShuai
 * @Date: 2021-06-27 19:28
 */
@Slf4j
public class ExceptionUtils {

    public ExceptionUtils() {

    }

    public static BaseResponse<Object> logAndResponse(String methodName, Throwable ex) {
        BaseResponse<Object> baseResponse = new BaseResponse();
        String errorCode = null;
        String message = null;
        if (ex instanceof BaseException) {
            BaseException bex = (BaseException)ex;
            errorCode = bex.getErrorCode();
            message = StringUtils.isEmpty(bex.getErrorTips()) ? bex.getMessage() : bex.getErrorTips();
            log.error("{} method error , code: {}, message: {}", new Object[]{methodName, errorCode, message});
        } else {
            StringBuilder stringBuilder;
            BindingResult result;
            if (ex instanceof BindException) {
                errorCode = ErrorCodeEnum.PARAM_ERROR.getCode();
                result = ((BindException)ex).getBindingResult();
                stringBuilder = new StringBuilder();
                List<FieldError> fieldErrors = result.getFieldErrors();
                if (CollectionUtils.isNotEmpty(fieldErrors)) {
                    fieldErrors.forEach(key -> {
                        stringBuilder.append(key.getField()).append(" ").append(key.getDefaultMessage()).append(", 当前值: '").append(key.getRejectedValue()).append("'; ");
                    });
                }
                message = stringBuilder.toString();
                log.error("{} method error , {}", methodName, message);
            } else if (ex instanceof MethodArgumentNotValidException) {
                errorCode = ErrorCodeEnum.PARAM_ERROR.getCode();
                result = ((MethodArgumentNotValidException)ex).getBindingResult();
                stringBuilder = new StringBuilder();
                List<FieldError> fieldErrors = result.getFieldErrors();
                if (CollectionUtils.isNotEmpty(fieldErrors)) {
                    fieldErrors.forEach(key -> {
                        stringBuilder.append(key.getField()).append(" ").append(key.getDefaultMessage()).append(", 当前值: '").append(key.getRejectedValue()).append("'; ");
                    });
                }
                message = stringBuilder.toString();
                log.error("{} method error , {}", methodName, message);
            } else if (ex instanceof ConstraintViolationException) {
                errorCode = ErrorCodeEnum.PARAM_ERROR.getCode();
                ConstraintViolationException cex = (ConstraintViolationException)ex;
                stringBuilder = new StringBuilder();
                Set<ConstraintViolation<?>> fieldErrors = cex.getConstraintViolations();
                if (CollectionUtils.isNotEmpty(fieldErrors)) {
                    fieldErrors.forEach(key -> {
                        String invald = key.getInvalidValue() != null ? key.getInvalidValue().toString() : "null";
                        stringBuilder.append(key.getPropertyPath()).append(" ").append(key.getMessage()).append(", 当前值: '").append(invald.length() < 50 ? invald : invald.substring(0, 47) + "...").append("'; ");
                    });
                }
                message = stringBuilder.toString();
                log.error("{} method error , {}", methodName, message);
            } else if (ex.getCause() instanceof JsonProcessingException) {
                errorCode = ErrorCodeEnum.JSON_PARSER_ERROR.getCode();
                JsonProcessingException cex = (JsonProcessingException)ex.getCause();
                message = "Json格式错误";
                log.error("{} method error , {}", methodName, org.apache.commons.lang3.exception.ExceptionUtils.getMessage(cex));
            } else {
                errorCode = ErrorCodeEnum.SYSTEM_ERROR.getCode();
                message = ErrorCodeEnum.SYSTEM_ERROR.getMessage();
                log.error(methodName + " method error , ", ex);
            }
        }

        baseResponse.setErrorTips(MDC.get("UUID"));
        baseResponse.setErrorCode(errorCode);
        baseResponse.setErrorMessage(message);
        baseResponse.setSuccess(false);
        return baseResponse;
    }
}
