package com.cloud.common.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 18:25
 * @version: V1.0
 */
public class EnumDeserializer implements ObjectDeserializer {

    public EnumDeserializer() {

    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object parseValue = parser.parse();
        String value = null;
        if (parseValue instanceof JSONObject) {
            JSONObject object = (JSONObject)parseValue;
            value = object.getString("value");
        } else if (parseValue instanceof String) {
            value = (String)parseValue;
        } else if (parseValue instanceof Integer) {
            value = String.valueOf(parseValue);
        }

        if (value != null) {
            Class<?> clazz = (Class)type;
            Object t = null;

            Object[] enumConstants;
            Object[] var10;
            int var11;
            int var12;
            Object enumConstant;
            String name;
            try {
                Method method = clazz.getDeclaredMethod("getEnumByValue", String.class);
                t = method.invoke((Object)null, value);
                if (t == null) {
                    enumConstants = clazz.getEnumConstants();
                    var10 = enumConstants;
                    var11 = enumConstants.length;

                    for(var12 = 0; var12 < var11; ++var12) {
                        enumConstant = var10[var12];
                        name = ((Enum)enumConstant).name();
                        if (value.equals(name)) {
                            t = enumConstant;
                            break;
                        }
                    }
                }
                return (T) t;
            } catch (Exception var15) {
                if (t == null) {
                    enumConstants = clazz.getEnumConstants();
                    var10 = enumConstants;
                    var11 = enumConstants.length;

                    for(var12 = 0; var12 < var11; ++var12) {
                        enumConstant = var10[var12];
                        name = ((Enum)enumConstant).name();
                        if (value.equals(name)) {
                            t = enumConstant;
                            break;
                        }
                    }
                }
                return (T) t;
            }
        } else {
            return null;
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
