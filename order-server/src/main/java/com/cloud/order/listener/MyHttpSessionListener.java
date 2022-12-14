package com.cloud.order.listener;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 监听器
 * @author: 周帅
 * @date: 2021/1/25 12:55
 * @version: V1.0
 */
@WebListener
@Slf4j
public class MyHttpSessionListener implements HttpSessionListener {

    /**
     * 在线人数
     */
    private AtomicInteger onlinePersonNum = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlinePersonNum.incrementAndGet();
        log.info("创建session，新用户上线");
        httpSessionEvent.getSession().getServletContext().setAttribute("onlinePersonNum",onlinePersonNum);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlinePersonNum.decrementAndGet();
        log.info("销毁session,用户下线");
        httpSessionEvent.getSession().getServletContext().setAttribute("onlinePersonNum",onlinePersonNum);
    }

}
