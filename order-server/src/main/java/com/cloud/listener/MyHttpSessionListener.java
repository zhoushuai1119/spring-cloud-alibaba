package com.cloud.listener;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @description: 监听器
 * @author: 周帅
 * @date: 2021/1/25 12:55
 * @version: V1.0
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    /**
     * 在线人数
     */
    private Integer onlinePersonNum = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        onlinePersonNum ++;
        System.out.println("创建session，新用户上线");
        httpSessionEvent.getSession().getServletContext().setAttribute("onlinePersonNum",onlinePersonNum);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        onlinePersonNum --;
        System.out.println("销毁5session,用户下线");
        httpSessionEvent.getSession().getServletContext().setAttribute("onlinePersonNum",onlinePersonNum);
    }
}
