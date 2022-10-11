package com.cloud.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Feign配置注册（全局）
 * @author: zhou shuai
 * @date: 2022/1/20 16:56
 * @version: v1
 */
@Configuration
@Slf4j
public class FeignSupportConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 解决seata的xid未传递
        String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            log.info("feign调用时seata全局事务ID:{}", xid);
            //可以设置请求头数据
            requestTemplate.header(RootContext.KEY_XID, xid);
        }
    }

}
