package com.cloud.controller;

import com.cloud.utils.LettuceRedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 周帅
 * @date: 2020/12/29 20:31
 * @version: V1.0
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private LettuceRedisUtil redisUtil;

    @RequestMapping("lock")
    public void lock() {
        RLock lock = redissonClient.getLock(System.currentTimeMillis()+"myLock");
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"获取到锁");
            //Integer ticket = Integer.parseInt(redisTemplate.opsForValue().get("ticket").toString());
            //redisUtil.set("ticket",30);
            Integer ticket = Integer.parseInt(redisUtil.get("ticket").toString());
            if (ticket > 0) {
                System.out.println("剩余库存:" + ticket);
            } else {
                System.out.println("库存不足:");
                return;
            }
            System.out.println("进行库存减1操作");
            //redisTemplate.opsForValue().set("ticket", String.valueOf(ticket - 1));
            redisUtil.set("ticket",String.valueOf(ticket - 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放锁");
        }
    }

}
