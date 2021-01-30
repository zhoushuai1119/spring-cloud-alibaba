package com.cloud.common.entity.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.cloud.common.enums.AgeEnum;
import com.cloud.common.serializer.EnumDeserializer;
import com.cloud.common.serializer.EnumSerializer;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:23
 * @version: V1.0
 */
@Data
public class EnumTest implements Serializable {

    private static final long serialVersionUID = -5308001741151787598L;

    private String name;

    @JSONField(serializeUsing = EnumSerializer.class,deserializeUsing = EnumDeserializer.class)
    private AgeEnum age;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
