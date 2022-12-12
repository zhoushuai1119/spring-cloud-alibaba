package com.cloud.order.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:31
 */
@Slf4j
@Component
public class NettyServer {

    public void start(InetSocketAddress address) {
        // 专门负责客户端连接; 并把连接注册到workerGroup的Selector中
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // bossGroup只是处理连接请求 ,真正的和客户端业务处理，会交给workerGroup完成
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap =
                    new ServerBootstrap()
                            // 绑定线程池
                            .group(bossGroup, workerGroup)
                            // 异步非阻塞的服务器端 TCP Socket 连接
                            .channel(NioServerSocketChannel.class)
                            // 打印日志
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .localAddress(address)
                            // 编码解码
                            .childHandler(new NettyServerChannelInitializer())
                            // 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
                            .option(ChannelOption.SO_BACKLOG, 128)
                            // 保持长连接，2小时无数据激活心跳机制
                            .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("netty服务器开始监听端口：" + address.getPort());
            // 给cf注册监听器，监听我们关心的事件
            /*future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("监听端口9000成功");
                    } else {
                        System.out.println("监听端口9000失败");
                    }
                }
            });*/
            // 等待服务端监听端口关闭，closeFuture是异步操作
            // 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅地关闭EventLoopGroup; 释放掉所有的资源，包括创建的线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
