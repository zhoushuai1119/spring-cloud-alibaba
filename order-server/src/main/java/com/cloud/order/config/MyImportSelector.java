package com.cloud.order.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/27 14:59
 * @version: v1
 */
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.cloud.order.domain.dto.test.TestC"};
    }

}
