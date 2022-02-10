package com.cloud.service;

import com.cloud.common.entity.common.SmartCarProtocol;
import com.cloud.common.service.common.NettySendService;
import com.cloud.netty.client.NettyClientHandler;
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
        SmartCarProtocol response = new SmartCarProtocol(content.getBytes().length, content.getBytes());
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
