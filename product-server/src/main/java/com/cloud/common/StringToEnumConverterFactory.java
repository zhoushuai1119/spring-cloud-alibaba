package com.cloud.common;

import com.cloud.common.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 18:59
 * @version: V1.0
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return new StringToEnum(aClass);
    }

    private class StringToEnum<T extends Enum<T> & BaseEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            source = source.trim();// 去除首尾空白字符
            return source.isEmpty() ? null : BaseEnum.valueOf(this.enumType, source);
        }
    }
}
