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

}
