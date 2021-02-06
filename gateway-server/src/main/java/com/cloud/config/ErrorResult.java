package com.cloud.config;

import lombok.Data;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/1 15:57
 * @version: V1.0
 */
@Data
public class ErrorResult {

    private int code;

    private String message;


    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
