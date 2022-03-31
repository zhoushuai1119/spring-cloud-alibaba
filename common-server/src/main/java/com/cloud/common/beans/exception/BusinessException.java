package com.cloud.common.beans.exception;


import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.platform.common.exception.BaseException;
import com.cloud.platform.common.exception.BaseExceptionCode;

/**
 * 业务Exception
 */
public class BusinessException extends BaseException {


    /**
     * @param errorCode 错误码
     * @param errorDetailMessage  错误详细信息
     */
    public BusinessException(ErrorCodeEnum errorCode, String errorDetailMessage) {
        super(errorCode.getCode(), errorDetailMessage, errorCode.getMessage());
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

}
