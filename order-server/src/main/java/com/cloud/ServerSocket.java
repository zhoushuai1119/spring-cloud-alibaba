package com.cloud;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-08 11:52
 */
public class ServerSocket {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(7777));

        ByteBuffer buffer = ByteBuffer.allocate(16);

        SocketChannel accept = serverSocket.accept();

        accept.read(buffer);

        String str = Charset.defaultCharset().decode(buffer).toString();
        System.out.println(str);

    }
}
