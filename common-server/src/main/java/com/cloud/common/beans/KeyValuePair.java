package com.cloud.common.beans;

import lombok.Data;

/**
 * <p><定义系统信息对对象>
 * <p><定义系统信息对对象>
 * <p>
 * @version [V1.0, 12]
 * @see [相关类/方法]
 */
@Data
public class KeyValuePair {
    private int key;
    private String value;
    private String descr;

    public KeyValuePair(){}

    public KeyValuePair(int key, String value, String descr) {
        this.key = key;
        this.value = value;
        this.descr = descr;
    }

}
