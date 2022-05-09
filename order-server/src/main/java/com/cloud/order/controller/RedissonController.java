package com.cloud.order.controller;

import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.common.utils.RedisUtil;
import com.cloud.order.common.exception.OrderErrorCodeEnum;
import com.cloud.order.filter.RedissionBloomFilter;
import com.cloud.platform.common.domain.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private RedissionBloomFilter redissionBloomFilter;

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
                return BaseResponse.createFailResult(OrderErrorCodeEnum.INSUFFICIENT_INVENTORY_ERROR);
            }
            redisUtil.set("ticket", ticket - 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("线程:{}释放锁",Thread.currentThread().getName());
        }
        return BaseResponse.createSuccessResult(null);
    }


    /**
     * 使用布隆过滤器 根据ID查询商品
     */
    @GetMapping("/filter/{id}")
    public BaseResponse<String> redission(@PathVariable Integer id){
        //先查询布隆过滤器，过滤掉不可能存在的数据请求
        if (!redissionBloomFilter.isExist(id)) {
            log.error("id:{},Redssion布隆过滤...",id);
            return BaseResponse.createFailResult(ErrorCodeEnum.NOT_LEGAL_ERROR);
        }
        //布隆过滤器认为可能存在，再走流程查询
        return BaseResponse.createSuccessResult(null);
    }

}
