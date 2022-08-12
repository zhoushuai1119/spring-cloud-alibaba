package com.cloud.order.enums;

import com.cloud.platform.web.validate.constraint.EnumValidator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @description: 下单途径枚举
 * @author: zhou shuai
 * @date: 2022/8/12 13:55
 * @version: v1
 */
@Getter
@AllArgsConstructor
public enum OrderWayEnum implements EnumValidator<Integer> {

    APP(1, "app下单"),
    WEB(2, "web下单"),
    SMALL_PROGRAM(3, "小程序下单");

    @JsonValue
    private Integer value;

    private String name;

    public static OrderWayEnum getEnumByValue(Integer value) {
        return Stream.of(values())
                .filter(orderWay -> Objects.equals(orderWay.getValue(), value))
                .findFirst()
                .orElse(null);
    }

}
