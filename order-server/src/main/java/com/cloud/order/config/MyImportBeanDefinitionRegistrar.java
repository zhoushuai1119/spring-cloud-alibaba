package com.cloud.order.config;

import com.cloud.order.domain.dto.test.TestD;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 15:04
 * @version: v1
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBean = new RootBeanDefinition(TestD.class);
        registry.registerBeanDefinition("testD",rootBean);
    }

}
