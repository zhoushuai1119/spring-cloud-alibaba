package com.cloud.controller;

import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.utils.ResultUtil;
import com.cloud.service.RabbitService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/directSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public Result<String> directSend(String msg) {
        rabbitService.directSender(msg);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    @RequestMapping("/fanoutSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public Result<String> fanoutSend(String msg) {
        rabbitService.fanoutSender(msg);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    @RequestMapping("/topicSendMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "信息", required = true, dataTypeClass = String.class)
    })
    public Result<String> topicSend(String msg) {
        rabbitService.topicSender(msg);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

}
