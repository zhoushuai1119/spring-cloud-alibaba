package com.cloud.order.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/6/24 11:13
 * @version: v1
 */
@Configuration
public class MinIoConfig {

    /**
     * Minio 服务端ip
     */
    @Value("${min.io.endpoint}")
    private String endpoint;

    @Value("${min.io.accessKey}")
    private String accessKey;

    @Value("${min.io.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
