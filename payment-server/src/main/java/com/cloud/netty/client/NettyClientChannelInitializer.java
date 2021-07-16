package com.cloud.netty.client;

import com.cloud.common.netty.decode.SmartCarDecoder;
import com.cloud.common.netty.encode.SmartCarEncoder;
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
        channel.pipeline().addLast("encoder", new SmartCarEncoder());
        channel.pipeline().addLast("decoder", new SmartCarDecoder());
        channel.pipeline().addLast(new NettyClientHandler());
    }

}
