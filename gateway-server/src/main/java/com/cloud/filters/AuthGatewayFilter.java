package com.cloud.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 局部过滤器
 * @author: 周帅
 * @date: 2021/2/1 11:03
 * @version: V1.0
 */
@Component
public class AuthGatewayFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("---进行局部AuthGatewayFilter过滤器---");
        //获取header的参数
        String name = exchange.getRequest().getHeaders().getFirst("name");
        String password = exchange.getRequest().getHeaders().getFirst("password");
        //boolean permitted = AuthUtil.isPermitted(name, password);//权限比较
        boolean permitted = true;
        if (permitted) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
