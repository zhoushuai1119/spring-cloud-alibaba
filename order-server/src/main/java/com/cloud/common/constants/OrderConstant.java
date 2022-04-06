package com.cloud.common.constants;


/**
 * @Author： Zhou Shuai
 * @Date： 17:49 2019/1/7
 * @Description：
 * @Version: 01
 */
public class OrderConstant {

    /**
     * 订单key
     */
    public interface CreateOrderKey {
        String CREATE_ORDER_KEY = "order:create";
    }


    /**
     * 登录人姓名
     */
    public interface CurrentLogin {

        /**
         * 系统默认用户名
         */
        String DEFAULT_USER_NAME = "system";

        String USER_NAME = "userName";
    }

}
