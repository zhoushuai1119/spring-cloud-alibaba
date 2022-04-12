package com.cloud.common.config;

import com.cloud.common.serializer.FastJson2JsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description: Redisson配置
 * @author: 周帅
 * @date: 2020/12/29 20:42
 * @version: V1.0
 */
@Configuration
@ConditionalOnProperty(
        prefix = "cloud.web.redis",
        name = {"enabled"},
        matchIfMissing = true
)
@Slf4j
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> template = new RedisTemplate <>();
        template.setConnectionFactory(redisConnectionFactory);

        FastJson2JsonRedisSerializer fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);

        // key采用String的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // value序列化方式采用fastJson
        template.setValueSerializer(fastJson2JsonRedisSerializer);
        // hash的value序列化方式采用fastJson
        template.setHashValueSerializer(fastJson2JsonRedisSerializer);
        // 开启事务
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}
