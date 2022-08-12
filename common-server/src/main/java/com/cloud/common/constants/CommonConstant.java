package com.cloud.common.constants;


/**
 * @Author： Zhou Shuai
 * @Date： 17:49 2019/1/7
 * @Description：
 * @Version: 01
 */
public class CommonConstant {
    /**
     * jwtToken 密钥
     */
    public interface jwtToken {
        String JWT_SECRET = "79e7c69681b8270162386e6daa53d1dc";
    }


    /**
     * 门类参数
     */
    public interface CategoryParam{
        /**
         * 顶级门类ID
         */
        String TOP_CATEGORY_ID = "0";
    }

    public interface EventCode {
        /**
         * 下单扣减库存 EventCode
         */
        String ORDER_PRODUCT_CODE = "ORDER_PRODUCT_REDUCE";
    }

}
