package com.cloud.dto.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 14:51
 * @version: v1
 */
@Slf4j
public class TestC {

    public TestC() {
        log.info("TestC constructor() ...");
    }

    public void print() {
        log.info("TestC print() ....");
    }

}
