package com.cloud.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author zhou shuai
 * @Date 2019/4/25
 * @Time 11:20
 */
@Component
@Data
public class EnvConfig {

    @Value("${spring.profiles.active}")
    private String env;

}
