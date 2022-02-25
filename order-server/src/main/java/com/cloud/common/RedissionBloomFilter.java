package com.cloud.common;

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

    private RBloomFilter<String> bloomFilter;

    /**
     * 执行顺序--》 Constructor >> @Autowired >> @PostConstruct
     */
    @PostConstruct
    private void init(){
        bloomFilter = redisson.getBloomFilter("phoneList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%
        bloomFilter.tryInit(100000000L,0.03);
        //测试数据
        for (int i = 0; i < 10; i++) {
            bloomFilter.add(String.valueOf(i));
        }
    }

    /**
     * 判断id是否可能存在
     */
    public boolean isExist(String id){
        return bloomFilter.contains(id);
    }

}
