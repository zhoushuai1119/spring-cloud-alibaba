package com.cloud.filters;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.cloud.config.ErrorResult;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/1 15:56
 * @version: V1.0
 */
public class GatewayHandler implements BlockRequestHandler {

    private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";


    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        System.out.println(request.getPath());
        return ServerResponse.status(200)
                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(buildErrorResult(throwable)));
                .body(BodyInserters.fromValue(buildErrorResult(request.getPath().value())));
    }


    private ErrorResult buildErrorResult(Throwable ex) {
//        return new ErrorResult(200, DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName());
        return new ErrorResult(500, "限流");
    }


    private ErrorResult buildErrorResult(String path) {
        if(path.equals("/spring/cloud/test/block/handler")){
            return new ErrorResult(200, "hhahahhahah限流");
        }
        return new ErrorResult(500, "限流");
    }
}
