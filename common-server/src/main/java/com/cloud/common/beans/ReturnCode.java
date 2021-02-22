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
    public static final KeyValuePair COMPLETE_TASK_ERROR = new KeyValuePair(-2000, "完成任务操作失败,该用户没有操作该任务权限","完成任务操作失败,该用户没有操作该任务权限");
    public static final KeyValuePair CLAIM_TASK_ERROR = new KeyValuePair(-2001, "拾取任务操作失败,该用户没有操作该任务权限","拾取任务操作失败,该用户没有操作该任务权限");
    public static final KeyValuePair RETURN_TASK_ERROR = new KeyValuePair(-2002, "回退任务操作失败,该用户没有操作该任务权限","回退任务操作失败,该用户没有操作该任务权限");

}
