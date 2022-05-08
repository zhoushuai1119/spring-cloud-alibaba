package com.cloud.order.netty.server;

import com.cloud.platform.web.netty.protocol.SmartProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:37
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 管理一个全局map，保存连接进服务端的通道数量
     */
    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();

        String clientIp = insocket.getAddress().getHostAddress();
        int clientPort = insocket.getPort();

        //获取连接通道唯一标识
        ChannelId channelId = ctx.channel().id();

        System.out.println();
        //如果map中不包含此连接，就保存连接
        if (CHANNEL_MAP.containsKey(channelId)) {
            log.info("客户端【" + channelId + "】是连接状态，连接通道数量: " + CHANNEL_MAP.size());
        } else {
            //保存连接
            CHANNEL_MAP.put(channelId, ctx);

            log.info("客户端【" + channelId + "】连接netty服务器[IP:" + clientIp + "--->PORT:" + clientPort + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();

        String clientIp = insocket.getAddress().getHostAddress();

        ChannelId channelId = ctx.channel().id();

        //包含此客户端才去删除
        if (CHANNEL_MAP.containsKey(channelId)) {
            //删除连接
            CHANNEL_MAP.remove(channelId);

            System.out.println();
            log.info("客户端【" + channelId + "】退出netty服务器[IP:" + clientIp + "--->PORT:" + insocket.getPort() + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端发消息会触发此函数
     * @return: void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 用于获取客户端发来的数据信息
        SmartProtocol body = (SmartProtocol) msg;
        log.info("加载客户端报文......");
        log.info("【" + ctx.channel().id() + "】" + " :" + body.toString());

        // 会写数据给客户端
        String str = "Hi I am Server ..." + body.getContent();
        SmartProtocol response = new SmartProtocol(str.getBytes().length,
                str.getBytes());
        // 当服务端完成写操作后，关闭与客户端的连接
        ctx.writeAndFlush(response);

        // 当有写操作时，不需要手动释放msg的引用
        // 当只有读操作时，才需要手动释放msg的引用

        //响应客户端
//        this.channelWrite(ctx.channel().id(), response);
    }

    /**
     * @param msg       需要发送的消息内容
     * @param channelId 连接通道唯一id
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 服务端给客户端发送消息
     * @return: void
     */
    public void channelWrite(ChannelId channelId, SmartProtocol msg) throws Exception {

        ChannelHandlerContext ctx = CHANNEL_MAP.get(channelId);

        if (ctx == null) {
            log.info("通道【" + channelId + "】不存在");
            return;
        }

        //将客户端的信息直接返回写入ctx
        ctx.writeAndFlush(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        String socketString = ctx.channel().remoteAddress().toString();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 发生异常会触发此函数
     * @return: void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println();
        ctx.close();
        log.info(ctx.channel().id() + " 发生了错误,此连接被关闭" + "此时连通数量: " + CHANNEL_MAP.size());
    }

}
