package com.cloud.common.utils;

import com.cloud.common.beans.exception.BusinessException;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 15:30
 * @version: v1
 */
@Slf4j
public class BusinessUtils {

    public static <T> T checkBaseRespose(BaseResponse<T> baseResponse, Object... params) {
        if (Objects.nonNull(baseResponse) && baseResponse.isSuccess()) {
            return baseResponse.getModel();
        }
        log.error("调用外部系统失败:{},参数:{}", JsonUtil.toString(baseResponse),JsonUtil.toString(params));
        throw new BusinessException(baseResponse.getErrorCode(),baseResponse.getErrorMessage());
    }

}
