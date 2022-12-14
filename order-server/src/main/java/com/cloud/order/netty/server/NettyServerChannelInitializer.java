package com.cloud.order.netty.server;

import com.cloud.platform.web.netty.codec.MessageCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:34
 */
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast("messageCodec",new MessageCodec());
        channel.pipeline().addLast(new NettyServerHandler());
    }
}
