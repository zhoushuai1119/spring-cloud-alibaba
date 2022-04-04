package com.cloud.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.cloud.platform.web.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
//用于返回枚举对象
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
// 实现BaseEnum主要用于用枚举接收前端参数；
// StringToEnumConverterFactory
public enum CategoryTypeEnum implements BaseEnum {

    ORDINARY_ARCHIVE(0, "普通档案"),
    SECRET_ARCHIVE(1, "机密档案");

    @EnumValue   //设置存入数据库的值
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
