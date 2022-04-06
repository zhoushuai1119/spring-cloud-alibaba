package com.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 过滤器
 * @author: 周帅
 * @date: 2021/1/25 11:51
 * @version: V1.0
 */
@WebFilter(filterName = "filterOne",urlPatterns = "/*")
@Order(value = 1)
@Slf4j
public class FilterOne implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("拦截器FilterOne 初始化init。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("访问路径:"+request.getRequestURI());
        filterChain.doFilter(request,response);
        log.info("*******************");
    }

    @Override
    public void destroy() {
        System.out.println("FilterOne destroy。。。。");
    }

}
