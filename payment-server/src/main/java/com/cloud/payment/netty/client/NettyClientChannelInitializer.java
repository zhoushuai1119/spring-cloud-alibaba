package com.cloud.payment.netty.client;

import com.cloud.platform.web.netty.codec.MessageCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:54
 */
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline().addLast("messageCodec",new MessageCodec());
        channel.pipeline().addLast(new NettyClientHandler());
    }

}
