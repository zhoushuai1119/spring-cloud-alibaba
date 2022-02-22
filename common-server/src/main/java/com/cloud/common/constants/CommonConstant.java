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
     * 日期格式
     */
    public interface dateFormat {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 当前登录用户
     */
    public interface ShiroCurrentUser {
        String SHIRO_CURRENT_USER = "shiroCurrentUser";
    }

    /**
     * 符号
     */
    public interface SymbolParam {
        /**
         * 逗号
         */
        String COMMA = ",";
        /**
         * 连接号
         */
        String HYPHEN = "-";
        /**
         * 波浪号
         */
        String WAVE = "~";
        /**
         * 分割字段和值的符号
         */
        String SEMICOLON = ":";
        /**
         * 分割字段和值得符号
         */
        String VALUE_SYMBOL = "?";
        /**
         * 字符串符号
         */
        String STRING_SYMBOL = "\"";
        /**
         * 空格
         */
        String SPACE_SYMBOL = " ";
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
     * Shiro错误信息
     */
    public interface ShiroError{
        /**
         * Shiro登录错误信息
         */
        String LOGIN_ERROR = "shiroLoginFailure";
        /**
         * Shiro验证码错误信息
         */
        String RANDOM_CODE_ERROR = "randomCodeError";
    }

    /**
     * 消息头
     */
    public interface netty{
        int HEAD_DATA = 0X76;
    }

    /**
     * RocketMQ--Topic头
     */
    public interface topic{
        /**
         * activiti-server TOPIC
         */
        String ACTIVITI_SERVER_TOPIC = "TP_ACTIVITI_SERVER_TOPIC";
        /**
         * activiti-server TRANSACTION TOPIC
         */
        String ACTIVITI_SERVER_TOPIC_TRANSACTION = "TP_ACTIVITI_SERVER_TOPIC_TRANSACTION";
        /**
         * order-server TOPIC
         */
        String ORDER_SERVER_TOPIC = "TP_ORDER_SERVER_TOPIC";
        /**
         * order-server TRANSACTION TOPIC
         */
        String ORDER_SERVER_TOPIC_TRANSACTION = "TP_ORDER_SERVER_TOPIC_TRANSACTION";
        /**
         * payment-server TOPIC
         */
        String PAYMENT_SERVER_TOPIC = "TP_PAYMENT_SERVER_TOPIC";
        /**
         * payment-server TRANSACTION TOPIC
         */
        String PAYMENT_SERVER_TOPIC_TRANSACTION = "TP_PAYMENT_SERVER_TOPIC_TRANSACTION";
        /**
         * product-server TOPIC
         */
        String PRODUCT_SERVER_TOPIC = "TP_PRODUCT_SERVER_TOPIC";
        /**
         * product-server TRANSACTION TOPIC
         */
        String PRODUCT_SERVER_TOPIC_TRANSACTION = "TP_PRODUCT_SERVER_TOPIC_TRANSACTION";
        /**
         * user-server TOPIC
         */
        String USER_SERVER_TOPIC = "TP_USER_SERVER_TOPIC";
        /**
         * user-server TRANSACTION TOPIC
         */
        String USER_SERVER_TOPIC_TRANSACTION = "TP_USER_SERVER_TOPIC_TRANSACTION";

    }

    /**
     * 定时任务发送的 TOPIC
     */
    public interface TimeTaskTopic {
        String TIME_TASK_TOPIC = "TP_F_SC";
    }

    /**
     * 定时任务回调的 TOPIC
     */
    public interface FeedBackTopic {

        String FEEDBACK_TASK_TOPIC = "TP_F_FB";

        String FEEDBACK_TASK_EVENTCODE = "EC_RESULT";
    }

    /**
     * 执行器
     */
    public interface executorHandler {
        String EXECUTOR_HANDLER = "mqJobHandler";
    }

}
