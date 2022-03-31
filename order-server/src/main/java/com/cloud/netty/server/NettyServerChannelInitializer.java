package com.cloud.netty.server;

import com.cloud.common.netty.decode.SmartDecoder;
import com.cloud.common.netty.encode.SmartEncoder;
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
        channel.pipeline().addLast("encoder",new SmartEncoder());
        channel.pipeline().addLast("decoder",new SmartDecoder());
        channel.pipeline().addLast(new NettyServerHandler());
    }
}
