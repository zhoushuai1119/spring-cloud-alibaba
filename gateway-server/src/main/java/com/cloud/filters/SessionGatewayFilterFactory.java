package com.cloud.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;

import java.time.Duration;

/**
 * Spring Cloud Gateway服务在默认情况下，请求结果之后，session会立刻过期。
 * 有些业务场景可能会在session中存储一些数据，比如登陆状态，如果登陆之后，
 * 长时间没有访问，再次访问的时候，让用户重新登陆等，都需要控制session的空闲时间
 */
@Service
@Slf4j
public class SessionGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            log.info("设置sesion过期时间***********");
            WebSession session = exchange.getSession().block();
            session.setMaxIdleTime(Duration.ofSeconds(60));
            return chain.filter(exchange);
        };
    }
}
