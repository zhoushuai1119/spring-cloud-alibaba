package com.cloud.common;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/21 13:38
 * @version: V1.0
 */
@Component
public class RedisBloomFilter {
    /**
     * 预计数据总量
     */
    private long size = 1000000;
    /**
     * 容错率
     */
    private double fpp = 0.01;
    /**
     * 二进制向量大小
     */
    private long numBits;
    /**
     * 哈希算法数量
     */
    private int numHashFunctions;

    /**
     * redis中的key
     */
    private final String key = "goods_filter";

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void init(){
        numBits = optimalNumOfBits();
        numHashFunctions = optimalNumOfHashFunctions();
        //测试数据
        for (int i = 0; i < 10; i++) {
            put(String.valueOf(i));
        }
    }

    /**
     * 向布隆过滤器中put
     */
    public void put(String id){
        long[] indexs = getIndexs(id);
        //将对应下标改为1
        for (long index : indexs) {
            redisTemplate.opsForValue().setBit(key, index, true);
        }
    }

    /**
     * 判断id是否可能存在
     */
    public boolean isExist(String id){
        long[] indexs = getIndexs(id);
        //只要有一个bit位为1就表示可能存在
        for (long index : indexs) {
            if (redisTemplate.opsForValue().getBit(key, index)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据key获取bitmap下标(算法借鉴)
     */
    private long[] getIndexs(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }

    /**
     * 计算哈希值(算法借鉴)
     */
    private long hash(String key) {
        Charset charset = Charset.defaultCharset();
        return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
    }

    /**
     * 计算二进制向量大小(算法借鉴)
     */
    private long optimalNumOfBits(){
        return (long)((double)(-size) * Math.log(fpp) / (Math.log(2.0D) * Math.log(2.0D)));
    }

    /**
     * 计算哈希算法数量(算法借鉴)
     */
    private int optimalNumOfHashFunctions() {
        return Math.max(1, (int)Math.round((double)numBits / (double)size * Math.log(2.0D)));
    }

}
