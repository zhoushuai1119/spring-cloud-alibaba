package com.cloud.enums;

import com.cloud.platform.web.enums.ConverterBaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
//用于返回枚举对象
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//枚举校验需要实现EnumValidator
public enum OrderEnum implements ConverterBaseEnum {

    ORDER_ONE(1, "订单1"),
    ORDER_TWO(2, "订单2");

    @JsonValue
    private Integer value;

    private String name;


    @JsonCreator
    public static OrderEnum getEnumByValue(Integer value) {
        return Stream.of(values())
                .filter(order -> Objects.equals(order.getValue(), value))
                .findFirst()
                .orElse(null);
    }

}
