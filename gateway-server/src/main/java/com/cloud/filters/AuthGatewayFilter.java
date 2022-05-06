package com.cloud.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
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
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
