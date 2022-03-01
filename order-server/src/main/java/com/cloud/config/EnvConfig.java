package com.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/1 14:25
 * @version: v1
 */
@Component
@Data
public class EnvConfig {

    @Value("${spring.profiles.active}")
    private String env;

}
