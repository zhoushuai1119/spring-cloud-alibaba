package com.cloud.common.beans;

import lombok.Data;

/**
 * <p><请求返回对象>
 * <p><请求返回对象>
 * <p>
 * @version [V1.0, 12]
 * @see [相关类/方法]
 */
@Data
public class Result<T> extends BaseResult {

    private T data;

    public Result() {
        super();
    }
    public Result(T data) {
        super();
        this.data =data;
    }
    public Result(String desc) {
        super(desc);
    }

    public Result(int code, String desc){
        super(code, desc);
    }

    public Result(KeyValuePair keyValuePair) {
        super(keyValuePair);
    }

    public Result(int code,String desc,boolean flag){
        super(code, desc,flag);
    }
}
