package com.cloud.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置类
 *
 * @author zhoushuai
 * @date 2021-05-29
 */
@Data
@ConfigurationProperties(prefix = "test.namespace")
public class TestNameSpaceProperties {
    /**
     * 测试参数2
     */
    private String param1;
    /**
     * 测试参数1
     */
    private String param2;

}