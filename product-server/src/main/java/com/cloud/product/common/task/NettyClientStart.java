package com.cloud.product.common.task;

import com.cloud.product.netty.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-28 18:00
 */
@Slf4j
@Component
public class NettyClientStart implements CommandLineRunner {

    @Autowired
    private NettyClient nettyClient;

    @Override
    public void run(String... args) {
        log.info("netty客户端启动****");
        //nettyClient.nettyClientStart();
    }

}
