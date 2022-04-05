package com.cloud.serializer;

import com.cloud.enums.OrderEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/5 18:24
 * @version: v1
 */
@Slf4j
public class SerializerTest extends JsonSerializer<OrderEnum> {

    @Override
    public void serialize(OrderEnum orderEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //log.info("orderEnum:{}",orderEnum);
    }

}
