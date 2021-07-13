package com.cloud.netty.client;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-07-07 11:58
 */
public class TestNettyClient {

    public static void main(String[] args) {
        //开启10条线程，每条线程就相当于一个客户端
//        for (int i = 1; i <= 10; i++) {

            new Thread(new NettyClient("thread" + "--")).start();
//        }
    }
}
