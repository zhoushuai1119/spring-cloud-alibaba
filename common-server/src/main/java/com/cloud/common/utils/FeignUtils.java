package com.cloud.common.utils;

import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.enums.ErrorCodeEnum;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 15:00
 * @version: v1
 */
public class FeignUtils {

    public static BusinessException decodeFeignException(String interfaceName,Throwable throwable){
        String message = throwable.getLocalizedMessage();
        return new BusinessException(ErrorCodeEnum.FEIGN_CLIENT_ERROR,interfaceName + "接口:" + message);
    }

}
