package com.cloud.payment.service;

import com.cloud.payment.netty.client.NettyClientHandler;
import com.cloud.common.netty.NettySendService;
import com.cloud.platform.web.netty.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-14 19:40
 */
@Service
@Slf4j
public class NettySendServiceImpl implements NettySendService<String> {

    @Override
    public void sendMessage(String content) {
        ConcurrentHashMap<ChannelId, ChannelHandlerContext> map = NettyClientHandler.CLIENT_MAP;
        MessageProtocol response = new MessageProtocol(content.getBytes().length, content.getBytes());
        if (!map.isEmpty()) {
            map.forEach((k, v) -> {
                if (v == null) {
                    log.info("通道【" + k + "】不存在");
                    return;
                }
                // 将客户端的信息直接返回写入ctx
                v.writeAndFlush(response);
            });
        }
    }
}
