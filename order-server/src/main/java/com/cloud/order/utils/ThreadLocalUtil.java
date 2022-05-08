package com.cloud.order.utils;

import com.cloud.order.domain.dto.ParmsTestDTO;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/2 20:42
 * @version: v1
 */
public class ThreadLocalUtil {

    private static ThreadLocal<ParmsTestDTO> parmsTest = new ThreadLocal<>();

    public static void set(ParmsTestDTO parmsTestDto) {
        parmsTest.set(parmsTestDto);
    }

    public static ParmsTestDTO get() {
        return parmsTest.get();
    }

    public static void remove() {
        parmsTest.remove();
    }

}
