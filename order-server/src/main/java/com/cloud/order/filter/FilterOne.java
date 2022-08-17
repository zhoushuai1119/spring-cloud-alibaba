package com.cloud.order.filter;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @description: 过滤器
 * @author: 周帅
 * @date: 2021/1/25 11:51
 * @version: V1.0
 */
@WebFilter(filterName = "filterOne", urlPatterns = "/*")
@Order(value = 1)
@Slf4j
public class FilterOne implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("filter init method");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String clientIP = ServletUtil.getClientIP(request, null);
        String servletPath = request.getServletPath();
        String currentTime = LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");

        log.info("{} {} 访问了 {}", currentTime, clientIP, servletPath);

        log.info("filter doFilter before");
        filterChain.doFilter(request, response);
        log.info("filter doFilter after");
    }

    @Override
    public void destroy() {
        log.info("filter destroy method");
    }

}
