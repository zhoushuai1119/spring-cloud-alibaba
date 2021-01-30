package com.cloud.common.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 18:23
 * @version: V1.0
 */
public class EnumSerializer  implements ObjectSerializer {

    public EnumSerializer() {

    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object != null) {
            JSONObject jsonObject = new JSONObject();

            try {
                Class<?> clazz = (Class)fieldType;
                Field codeField = clazz.getDeclaredField("value");
                codeField.setAccessible(true);
                jsonObject.put(codeField.getName(), codeField.get(object));
                Field valueField = clazz.getDeclaredField("name");
                valueField.setAccessible(true);
                jsonObject.put(valueField.getName(), valueField.get(object));
            } catch (Exception var10) {
                serializer.write("");
            }

            serializer.write(jsonObject);
        } else {
            serializer.write("");
        }
    }
}
