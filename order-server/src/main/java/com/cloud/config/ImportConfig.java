package com.cloud.config;

import com.cloud.config.properties.ApolloProperties;
import com.cloud.config.properties.TestNameSpaceProperties;
import com.cloud.dto.test.TestA;
import com.cloud.dto.test.TestB;
import com.cloud.dto.test.TestFConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 14:51
 * @version: v1
 */
@Import({TestA.class, TestB.class, MyImportSelector.class,
        MyImportBeanDefinitionRegistrar.class, TestFConfig.class})
@Configuration
@EnableConfigurationProperties({ApolloProperties.class, TestNameSpaceProperties.class})
public class ImportConfig {

}
