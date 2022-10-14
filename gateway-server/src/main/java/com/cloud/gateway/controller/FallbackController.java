package com.cloud.gateway.controller;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.common.enums.BaseErrorCodeEnum;
import com.cloud.platform.common.exception.BaseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 默认降级处理
 * @author: 周帅
 * @date: 2021/2/3 13:47
 * @version: V1.0
 */
@RestController
public class FallbackController {

    @RequestMapping("fallback")
    public BaseResponse fallback(){
        return BaseResponse.createFailResult(
                new BaseException(BaseErrorCodeEnum.REQUEST_FAIL_FALLBACK.getCode(),
                        BaseErrorCodeEnum.REQUEST_FAIL_FALLBACK.getMessage(), null
                ));
    }

}
