package com.cloud.order.utils;

import com.cloud.order.common.constants.OrderConstant;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/8/12 15:15
 * @version: v1
 */
public class RedisLockKeyUtil {

    /**
     * 获取redis分布式锁的lockKey
     * @param orderWayEnum
     * @param dev
     * @param key
     * @return
     */
    public static String getLockKey(Integer orderWay, String env, String key) {
        return OrderConstant.System.SYSTEM_CODE + "_" + env + "_" + orderWay + "_" + key.toLowerCase();
    }

}
