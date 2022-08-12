package com.cloud.order.controller;


import com.cloud.order.domain.dto.OrderParamDTO;
import com.cloud.order.service.OrderDetailService;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.web.aop.annotation.MethodLogger;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 创建订单接口
     *
     * @return
     */
    @ApiOperation(value = "创建订单接口", notes = "创建订单接口")
    @PostMapping("create")
    @MethodLogger
    public BaseResponse createOrder(@RequestBody @Validated OrderParamDTO createOrderParamDTO) {
        log.info("createOrder currentThreadId:{}", Thread.currentThread().getId());
        return orderDetailService.createOrder(createOrderParamDTO);
    }

}

