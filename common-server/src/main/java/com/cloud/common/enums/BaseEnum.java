package com.cloud.common.enums;

import java.util.Objects;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 18:47
 * @version: V1.0
 */
public interface BaseEnum {

    /**
     * 序列化
     *
     * @return 不允许返回 null
     */
    Object toValue();

    /**
     * 反序列化
     *
     * @param enumType 实际枚举类型
     * @param value    当前值
     * @param <T>      枚举类型并且实现 {@link BaseEnum} 接口
     * @return 枚举常量
     */
    static <T extends Enum<T> & BaseEnum> T valueOf(Class<T> enumType, Object value) {
        if (enumType == null || value == null) {
            return null;
        }
        System.out.println("value:" + value);
        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            Object enumValue = enumConstant.toValue();
            if (Objects.equals(enumValue, value)
                    || Objects.equals(enumValue.toString(), value.toString())) {
                return enumConstant;
            }
        }
        return null;
    }

}
