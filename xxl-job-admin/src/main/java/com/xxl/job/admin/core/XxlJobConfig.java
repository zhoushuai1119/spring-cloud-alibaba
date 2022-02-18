package com.xxl.job.admin.core;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class XxlJobConfig {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses("http://139.196.208.53:19090/xxl-job-admin");
        xxlJobSpringExecutor.setAppname("mq-executor");
        //xxlJobSpringExecutor.setAddress(address);
        //xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(0);
        //xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath("/${user.home}/xxl-job/${spring.application.name}/jobhandler");
        xxlJobSpringExecutor.setLogRetentionDays(30);

        return xxlJobSpringExecutor;
    }

}
