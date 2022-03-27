package com.cloud.config;

import com.cloud.dto.test.TestA;
import com.cloud.dto.test.TestB;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 14:51
 * @version: v1
 */
@Import({TestA.class, TestB.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
public class ImportConfig {

}
