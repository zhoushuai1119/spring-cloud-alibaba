package com.cloud.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置类
 *
 * @author zhoushuai
 * @date 2021-05-29
 */
@Data
@ConfigurationProperties(prefix = "cloud.web.redisson")
public class RedissonProperties {
    /**
     * 节点信息 host:port
     */
    private String nodes;
    /**
     * 密码
     */
    private String password;
    /**
     * 超时时间
     */
    private Integer timeout = 3000;
    /**
     * 集群状态扫描间隔时间，单位是毫秒
     */
    private Integer scanInterval = 1000;
    /**
     * 连接池大小
     */
    private Integer connectionPoolSize = 64;
    /**
     * 最小空闲连接
     */
    private Integer connectionMinimumIdleSize = 10;

    private int pingConnectionInterval = 1000;

}