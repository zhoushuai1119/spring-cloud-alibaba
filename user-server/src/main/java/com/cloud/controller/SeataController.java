package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.proxy.OrderProxy;
import com.cloud.proxy.PaymentProxy;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: seata测试
 * @author: zhou shuai
 * @date: 2022/1/21 10:51
 * @version: v1
 */
@RestController
@RequestMapping("/seata")
@Slf4j
public class SeataController {

    @Autowired
    private OrderProxy orderProxy;

    @Autowired
    private PaymentProxy paymentProxy;

    /**
     * seata分布式事务测试
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @RequestMapping("/seataTest")
    public BaseResponse seataTest() {
        orderProxy.updateCategory();
        log.info("*********");
        paymentProxy.saveUser();
        return BaseResponse.createSuccessResult(null);
    }

}