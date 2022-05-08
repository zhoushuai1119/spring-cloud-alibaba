package com.cloud.order.filter;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/21 14:39
 * @version: V1.0
 */
@Component
public class RedissionBloomFilter {

    @Autowired
    private RedissonClient redisson;

    private RBloomFilter<Integer> bloomFilter;

    /**
     * 预计数据总量
     */
    private long size = 20;
    /**
     * 容错率
     */
    private double fpp = 0.001;
    /**
     * redis中的key
     */
    private final String key = "phoneList";

    /**
     * 执行顺序--》 Constructor >> @Autowired >> @PostConstruct
     */
    @PostConstruct
    private void init(){
        bloomFilter = redisson.getBloomFilter(key);
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(size,fpp);
        //测试数据
        for (int i = 0; i < 10; i++) {
            if (!isExist(i)) {
                bloomFilter.add(i);
            }
        }
    }

    /**
     * 判断id是否可能存在
     */
    public boolean isExist(Integer id){
        return bloomFilter.contains(id);
    }

}
