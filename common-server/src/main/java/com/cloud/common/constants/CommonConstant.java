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
     * 门类参数
     */
    public interface CategoryParam{
        /**
         * 顶级门类ID
         */
        String TOP_CATEGORY_ID = "0";
    }

}
