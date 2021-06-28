package com.cloud.common.exception;


import com.cloud.common.beans.exception.BaseException;
import com.cloud.common.beans.exception.BaseExceptionCode;
import com.cloud.common.enums.ErrorCodeEnum;

/**
 * 业务Exception
 *
 * @Author 施文
 * @Date 2019/06/23
 * @Time 13:46:19
 */
public class BusinessException extends BaseException {

    /**
     * @param errorCode 错误
     * @param no        单号
     */
    public BusinessException(ErrorCodeEnum errorCode, Long no) {
        super(errorCode.getCode(), errorCode.getMessage(), "no:" + no + ", " + errorCode.getMessage());
    }

    /**
     * @param errorCode 错误
     * @param no        单号
     */
    public BusinessException(ErrorCodeEnum errorCode, String no) {
        super(errorCode.getCode(), errorCode.getMessage(), "no:" + no + ", " + errorCode.getMessage());
    }

    public BusinessException(ErrorCodeEnum errorCode) {
        super(errorCode.getCode(), errorCode.getMessage(), errorCode.getMessage());
    }

    public BusinessException(BaseExceptionCode errorCode) {
        super(errorCode.getErrorCode(), errorCode.getErrorMessage(), errorCode.getErrorTips());
    }

    public BusinessException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage, errorMessage);
    }

    /**
     * @param errorCode 错误
     */
    public static void failBuild(boolean isError, ErrorCodeEnum errorCode) {
        if (isError) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * @param errorCode 错误
     */
    public static void failBuild(boolean isError, String errorCode, String message) {
        if (isError) {
            throw new BusinessException(errorCode, message);
        }
    }
}
