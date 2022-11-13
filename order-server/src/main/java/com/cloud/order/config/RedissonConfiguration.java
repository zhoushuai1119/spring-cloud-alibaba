package com.cloud.order.config;

import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Redisson配置
 * @author: 周帅
 * @date: 2020/12/29 20:42
 * @version: V1.0
 */
@Configuration
@ConditionalOnClass(Config.class)
@ConditionalOnProperty(
        prefix = "cloud.web.redisson",
        name = {"enabled"}
)
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
public class RedissonConfiguration {

    @Value("${spring.application.name:APP}")
    private String clientName;

    @Bean
    public RedissonClient redissonClient(final RedissonProperties redissonProperties) {
        log.info("start RedissonClient,config:{}", JsonUtil.toString(redissonProperties));

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + redissonProperties.getNodes());
        singleServerConfig.setTimeout(redissonProperties.getTimeout());
        singleServerConfig.setClientName(clientName);
        if (StringUtils.isNotEmpty(redissonProperties.getPassword())) {
            singleServerConfig.setPassword(redissonProperties.getPassword());
        }
        singleServerConfig.setConnectionPoolSize(redissonProperties.getConnectionPoolSize());
        singleServerConfig.setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        singleServerConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
        return Redisson.create(config);
    }

}
