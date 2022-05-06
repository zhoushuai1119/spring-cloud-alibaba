package com.cloud.controller;

import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.common.utils.RedisUtil;
import com.cloud.platform.common.enums.BaseErrorCodeEnum;
import com.cloud.platform.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: 周帅
 * @date: 2020/12/29 20:31
 * @version: V1.0
 */
@RestController
@RequestMapping("/redisson")
@Slf4j
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        log.info("执行init,设置redis.....");
        redisUtil.set("ticket", 20);
    }

    @PostMapping("lock")
    public BaseResponse<String> lock(@RequestParam(required = true) String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();
            log.info("线程:{}获取到锁", Thread.currentThread().getName());
            Integer ticket = Integer.parseInt(redisUtil.get("ticket").toString());
            if (ticket > 0) {
                log.info("剩余库存:{}",ticket);
            } else {
                log.error("库存不足");
                return BaseResponse.createFailResult(ErrorCodeEnum.INSUFFICIENT_INVENTORY_ERROR);
            }
            redisUtil.set("ticket", ticket - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.createFailResult(BaseErrorCodeEnum.SYSTEM_ERROR);
        } finally {
            lock.unlock();
            log.info("线程:{}释放锁",Thread.currentThread().getName());
        }
        return BaseResponse.createSuccessResult(null);
    }

}
