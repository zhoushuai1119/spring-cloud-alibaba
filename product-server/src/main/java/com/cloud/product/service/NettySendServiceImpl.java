package com.cloud.product.service;

import com.cloud.common.netty.NettySendService;
import com.cloud.product.netty.client.NettyClientHandler;
import com.cloud.platform.web.netty.protocol.SmartProtocol;
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
        SmartProtocol response = new SmartProtocol(content.getBytes().length, content.getBytes());
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
