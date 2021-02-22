package com.cloud.common.utils;

import java.util.UUID;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/22 10:48
 * @version: V1.0
 */
public class UUIDutil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
