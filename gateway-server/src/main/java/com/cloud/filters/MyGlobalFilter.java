package com.cloud.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 全局过滤器-作用在所有的路由上
 * @author: 周帅
 * @date: 2021/2/1 10:54
 * @version: V1.0
 */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //拦截方法，拦截到请求后，自动执行
        System.out.println("filter 拦截方法，拦截到请求后，自动执行 ");
        //以后开发中，不会将 user对象存到session，只会在地址上带上token
        //根据token是否有空可以判断是否登录
        //http://localhost:8001/users/3?token=10001&pageSize=30
        //String token =  exchange.getRequest().getQueryParams().getFirst("token");
        String token = "token";
        if(token == null || "".equals(token)){
            //未登录 跳转到登录页
            System.out.println("----跳到登录页面");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);//2 全部放行
    }

    /**
     * 系统调用该方法获得过滤器的优先级,返回的数字越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return -10;
    }

}
