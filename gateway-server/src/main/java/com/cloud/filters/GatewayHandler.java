package com.cloud.filters;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.cloud.platform.common.enums.BaseErrorCodeEnum;
import com.cloud.platform.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(buildErrorResult()));
    }


    private BaseResponse buildErrorResult() {
        return BaseResponse.createFailResult(BaseErrorCodeEnum.REQUEST_FAIL_BLOCKED_BY_SENTINEL);
    }

}
