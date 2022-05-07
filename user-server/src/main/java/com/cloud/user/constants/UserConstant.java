package com.cloud.user.constants;


/**
 * @Author： Zhou Shuai
 * @Date： 17:49 2019/1/7
 * @Description：
 * @Version: 01
 */
public class UserConstant {

    /**
     * 登录toekn
     */
    public interface Token {

        /**
         * 系统默认用户名
         */
        String TOEKN = "token";

    }

    /**
     * 验证码
     */
    public interface RandomCode {
        /**
         * 验证码Redis key
         */
        String RANDOM_CODE_KEY = "randomCode";
        /**
         * 验证码校验失败
         */
        String RANDOM_CODE_CHECK_EXCEPTION = "randomCodeCheckException";
        /**
         * 验证码已过期
         */
        String RANDOM_CODE_EXPIRE_EXCEPTION = "randomCodeExpireException";
    }

    /**
     * 当前登录用户
     */
    public interface CurrentUser {
        String CURRENT_USER = "user";
    }

}
