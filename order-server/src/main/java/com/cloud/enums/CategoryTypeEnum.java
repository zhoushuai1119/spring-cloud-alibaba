package com.cloud.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.cloud.platform.web.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/3 20:03
 * @version: v1
 */
@AllArgsConstructor
@Getter
public enum CategoryTypeEnum implements BaseEnum {

    ORDINARY_ARCHIVE(0, "普通档案"),
    SECRET_ARCHIVE(1, "机密档案");

    @EnumValue
    //@JsonValue //标记json返回的值
    private Integer value;

    private String name;


    public static CategoryTypeEnum getEnumByValue(String value) {
        return Stream.of(values())
                .filter(type -> Objects.equals(type.getValue().toString(), value))
                .findFirst()
                .orElse(null);
    }

}
