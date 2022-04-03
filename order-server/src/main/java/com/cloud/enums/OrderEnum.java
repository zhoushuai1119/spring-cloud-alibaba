package com.cloud.enums;

import com.cloud.platform.web.validate.constraint.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:59
 * @version: V1.0
 */
@AllArgsConstructor
@Getter
public enum OrderEnum implements EnumValidator<Integer> {

    ORDER_ONE(1, "订单1"),
    ORDER_TWO(2, "订单2");

    private Integer value;
    private String name;


    public static OrderEnum getEnumByValue(Integer value) {
        return Stream.of(values())
                .filter(order -> Objects.equals(order.getValue(), value))
                .findFirst()
                .orElse(null);
    }

}
