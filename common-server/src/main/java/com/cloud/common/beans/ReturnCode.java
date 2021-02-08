package com.cloud.common.beans;

/**
 * <p><ReturnCode>
 * <p><定义系统请求返回码与返回描述信息>
 * <p>
 * @author taoxue2
 * @version [V1.0, 12]
 * @see [相关类/方法]
 */
public class ReturnCode {

	private ReturnCode(){}

    public static final KeyValuePair SUCCESS = new KeyValuePair(0, "操作成功","操作成功");
    public static final KeyValuePair ACCESS_ADDRESS_EXCEPTION = new KeyValuePair(404, "访问地址不存在","访问地址不存在");
    public static final KeyValuePair TOKEN_ERROR = new KeyValuePair(402,"token错误或失效", "token错误或失效");
    public static final KeyValuePair PARAM_ERROR = new KeyValuePair(-404, "参数错误","参数错误");
    public static final KeyValuePair INTERNAL_ERROR = new KeyValuePair(500, "服务端内部错误","服务端内部错误");

}
