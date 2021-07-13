package com.cloud;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-08 11:52
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",7777));

        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.write(buffer);
    }
}
