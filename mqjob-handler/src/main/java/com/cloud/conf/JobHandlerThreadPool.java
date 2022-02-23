package com.cloud.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/23 14:52
 * @version: v1
 */
@Data
@ConfigurationProperties(prefix = "mq.handler")
public class JobHandlerThreadPool {

    private int corePoolSize;

    private int maximumPoolSize;

    private int keepAliveTime;

}
