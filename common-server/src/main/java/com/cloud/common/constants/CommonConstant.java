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

    /**
     * rocketmq的 EventCode
     */
    public interface EventCode {

    }

    /**
     * redis 分布式锁key
     */
    public interface RedisLockKey {
        /**
         * 创建订单
         */
        String CREATE_ORDER_KEY = "order:create";
        /**
         * 购买商品扣减库存
         */
        String PURCHASE_PRODUCT_KEY = "product:purchase";
    }

    /**
     * 服务系统编码
     */
    public interface  SystemCode {
        /**
         * 订单服务
         */
        String ORDER_SERVER = "order-server";
        /**
         * 支付服务
         */
        String PAYMENT_SERVER = "payment-server";
        /**
         * 商品服务
         */
        String PRODUCT_SERVER = "product-server";
        /**
         * 用户服务
         */
        String USER_SERVER = "user-server";
    }

    /**
     * seninel TokenServer
     */
    public interface TokenServer {

        /**
         * token server namespace
         */
        String TOKEN_SERVER_NAMESPACE = "development.token-server";

        /**
         * token server rule key
         */
        String TOKEN_SERVER_RULE_KEY = "token-server-cluster-map";

        /**
         * default rules
         */
        String DEFAULT_RULES = "[]";

    }


}
