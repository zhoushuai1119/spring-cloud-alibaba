package com.cloud.order.enums;

import com.cloud.platform.web.validate.constraint.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:59
 * @version: V1.0
 */
@AllArgsConstructor
@Getter
//枚举校验需要实现EnumValidator
public enum EnumCheckTest implements EnumValidator<Integer> {

    CHECK_TEST_ONE(1, "测试1"),
    CHECK_TEST_TWO(2, "测试2"),
    CHECK_TEST_THREE(3, "测试3");

    private Integer value;
    private String name;

}
