package com.cloud.order.utils;

import com.cloud.order.domain.dto.ParmsTestDto;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/2 20:42
 * @version: v1
 */
public class ThreadLocalUtil {

    private static ThreadLocal<ParmsTestDto> parmsTest = new ThreadLocal<>();

    public static void set(ParmsTestDto parmsTestDto) {
        parmsTest.set(parmsTestDto);
    }

    public static ParmsTestDto get() {
        return parmsTest.get();
    }

    public static void remove() {
        parmsTest.remove();
    }

}
