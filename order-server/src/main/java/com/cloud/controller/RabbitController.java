package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.service.RabbitService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : shuaizhou4
 * @version : [V1.0]
 * @description:
 * @Date ： 2020-01-13 18:52
 */
@RestController
@RequestMapping("rabbit")
public class RabbitController {

    @Autowired
    private RabbitService rabbitService;

    @PostMapping("/directSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public BaseResponse<String> directSend(String msg) {
        rabbitService.directSender(msg);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/fanoutSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public BaseResponse<String> fanoutSend(String msg) {
        rabbitService.fanoutSender(msg);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/topicSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public BaseResponse<String> topicSend(String msg) {
        rabbitService.topicSender(msg);
        return BaseResponse.createSuccessResult(null);
    }

}
