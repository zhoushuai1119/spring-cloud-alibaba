package com.cloud.filters;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
        //根据token是否有空可以判断是否登录
        //String token = exchange.getRequest().getHeaders().getFirst("token");
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(StringUtils.isEmpty(token)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String msg = "token is null";
            DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes());
            return response.writeWith(Mono.just(buffer));
            //return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
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
