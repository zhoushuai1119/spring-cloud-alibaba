package com.cloud.payment.netty.client;

import com.cloud.platform.web.netty.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:55
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 计算有多少客户端接入，第一个string为客户端ip
     */
    public static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        log.info("客户端开始连接。。。。。。");
        CLIENT_MAP.put(ctx.channel().id(), ctx);

        log.info("ClientHandler Active");
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有服务端端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.close();
        log.info("服务端终止了服务");
    }

    /**
     * 只是读数据，没有写数据的话
     * 需要自己手动的释放的消息
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // 用于获取客户端发来的数据信息
            MessageProtocol body = (MessageProtocol) msg;
            log.info("服务器返回客户端数据:" + body.getContent());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("服务端发生异常【" + cause.getMessage() + "】");
        ctx.close();
    }

    /**
     * @param msg       需要发送的消息内容
     * @param channelId 连接通道唯一id
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 客户端给服务端发送消息
     * @return: void
     */
    public void channelWrite(ChannelId channelId, MessageProtocol msg) {

        ChannelHandlerContext ctx = CLIENT_MAP.get(channelId);

        if (ctx == null) {
            log.info("通道【" + channelId + "】不存在");
            return;
        }

        //将客户端的信息直接返回写入ctx
        ctx.writeAndFlush(msg);
    }

}
