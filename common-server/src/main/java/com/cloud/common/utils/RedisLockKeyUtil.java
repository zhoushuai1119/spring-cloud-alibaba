package com.cloud.common.utils;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/8/12 15:15
 * @version: v1
 */
public class RedisLockKeyUtil {

    /**
     * 获取redis分布式锁的lockKey
     *
     * @param systemCode 系统编码
     * @param lockKey    lockKey
     * @param dev        环境
     * @param businessId 业务ID
     * @return
     */
    public static String getLockKey(String systemCode, String env, String lockKey, String businessId) {
        return systemCode + "_" + env + "_" + lockKey.toLowerCase() + "_" + businessId;
    }

}
