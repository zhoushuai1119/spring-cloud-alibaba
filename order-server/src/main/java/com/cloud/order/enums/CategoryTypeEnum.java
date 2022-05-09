package com.cloud.order.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.cloud.platform.web.enums.ConverterBaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
// 实现 ConverterBaseEnum 主要用于用枚举接收前端参数；StringToEnumConverterFactory
// 用枚举接收前端参数需要注意:
// 1: 枚举类中value以0开始，依次增加； 枚举类中以value的值作为顺序，依次排列
// 2: 枚举类在接收前端值的时候看的是ordinal(顺序从0开始)这个值;一定要让这个值跟value相等，不然就会出现bug
// 3: 阿里开发手册：支持用枚举接收参数，但不建议接口返回值使用枚举类型或包含枚举类型的POJO对象
public enum CategoryTypeEnum implements ConverterBaseEnum {

    ORDINARY_ARCHIVE(0, "普通档案"),
    SECRET_ARCHIVE(1, "机密档案");

    @EnumValue //设置存入数据库的值
    @JsonValue //标记json返回的值
    private Integer value;

    private String name;

    /**
     * 添加 @JsonCreator 可以解决上面前端传参绑定ordinal的问题；实现绑定value来获取枚举
     * 由于已经覆盖了原来的序列化/反序列化方式，所以 ordinal 的支持已经失效。
     * 参考博客: https://blog.csdn.net/alinyua/article/details/86383254
     *
     * @param value
     * @return
     */
    @JsonCreator
    public static CategoryTypeEnum getEnumByValue(Integer value) {
        return Stream.of(values())
                .filter(type -> Objects.equals(type.getValue(), value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No element matches " + value));
    }

}
