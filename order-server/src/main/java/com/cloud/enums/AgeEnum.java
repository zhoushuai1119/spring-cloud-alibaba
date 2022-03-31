package com.cloud.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.cloud.common.enums.BaseEnum;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/25 17:59
 * @version: V1.0
 */
public enum AgeEnum implements IEnum<String>, BaseEnum {

    AGEONE("周帅","11"),
    AGETWO("流通","22");

    private String name;
    private String value;

    public static AgeEnum valuesOf(String value){
        for (AgeEnum rs : AgeEnum.values()){
            if (rs.getValue().equals(value)){
                return rs;
            }
        }
        return null;
    }

    AgeEnum(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object toValue() {
        return value;
    }
}
