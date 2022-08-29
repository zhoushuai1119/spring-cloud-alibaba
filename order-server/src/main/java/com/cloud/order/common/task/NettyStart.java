package com.cloud.order.common.task;

import com.cloud.order.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-28 18:00
 */
@Slf4j
@Component
public class NettyStart implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        log.info("netty启动地址:{}",inetSocketAddress.getAddress());
        nettyServer.start(inetSocketAddress);
    }

}
