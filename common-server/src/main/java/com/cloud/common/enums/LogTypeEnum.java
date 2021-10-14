package com.cloud.common.enums;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:59
 */
public enum LogTypeEnum {

    PARAM("param log"),
    RETURN("return log"),
    FULL("param and return log");

    private String message;

    private LogTypeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
