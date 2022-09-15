package com.cloud.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redisson配置类
 *
 * @author zhoushuai
 * @date 2021-05-29
 */
@Data
@Component
@ConfigurationProperties(prefix = "apollo.properties.test")
public class ApolloPropertiesTest {

    private String aa;
    private String bb;
    private String cc;

}
