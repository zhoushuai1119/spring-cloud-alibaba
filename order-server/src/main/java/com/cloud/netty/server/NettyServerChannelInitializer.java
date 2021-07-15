package com.cloud.netty.server;

import com.cloud.common.netty.decode.SmartCarDecoder;
import com.cloud.common.netty.encode.SmartCarEncoder;
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
        channel.pipeline().addLast("encoder",new SmartCarEncoder());
        channel.pipeline().addLast("decoder",new SmartCarDecoder());
        channel.pipeline().addLast(new NettyServerHandler());
    }
}
