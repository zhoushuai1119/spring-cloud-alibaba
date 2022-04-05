package com.cloud.serializer;

import com.cloud.enums.OrderEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/5 18:29
 * @version: v1
 */
@Slf4j
public class DeserializeTest extends JsonDeserializer<OrderEnum> {


    @Override
    public OrderEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        log.info("text:{},value:{}",jsonParser.getText(),jsonParser.getValueAsString());
        OrderEnum enumByValue = OrderEnum.getEnumByValue(jsonParser.getIntValue());
        if (Objects.nonNull(enumByValue)) {
            return enumByValue;
        }
        return null;
    }

}
