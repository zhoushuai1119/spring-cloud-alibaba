package com.cloud.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.cloud.filters.AuthGatewayFilter;
import com.cloud.filters.GatewayHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/1 10:34
 * @version: V1.0
 */
@Configuration
public class GateWayConfig {

    @Autowired
    private AuthGatewayFilter authGatewayFilter;

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GateWayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        initCustomizedApis();
        initGatewayRules();
        //自定义处理被限流的请求
        GatewayCallbackManager.setBlockHandler(new GatewayHandler());
    }

    /**
     * 全局过滤器-统计请求耗时
     *
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter elapsedGlobalFilter() {
        return (exchange, chain) -> {
            //调用请求之前统计时间
            Long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then().then(Mono.fromRunnable(() -> {
                //调用请求之后统计时间
                Long endTime = System.currentTimeMillis();
                System.out.println(
                        exchange.getRequest().getURI().getRawPath() + ", cost time : " + (endTime - startTime) + "ms");
            }));
        };
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //http://localhost:8762/order-server/order-server/category/getCategory?categoryId=1
        routes.route("order-server",
                r -> r.path("/api/order-server/**")
                        .filters(f -> f.stripPrefix(1).filter(authGatewayFilter))
                        .uri("lb://order-server")
        ).build();
        //http://localhost:8762/user-server/user-server/user/getAuthCodeImg
        routes.route("user-server",
                r -> r.path("/api/user-server/**")
                        .filters(f -> f.stripPrefix(1).filter(authGatewayFilter))
                        .uri("lb://user-server")
        ).build();
        routes.route("camunda-server",
                r -> r.path("/api/camunda-server/**")
                        .filters(f -> f.stripPrefix(1).filter(authGatewayFilter))
                        .uri("lb://camunda-server")
        ).build();
        return routes.build();
    }

    /**
     * ApiDefinition：用户自定义的API定义分组，可以看做是一些URL匹配的组合。
     * 就像我们可以定义一个API一样my_api，请求路径模式为/foo/**和/baz/**的都归到my_api这个API分组。
     * 限流的时候可以针对这个自定义的API分组维度进行限流。
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition apiOrder = new ApiDefinition("api-order-server")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {
                    {
                        add(new ApiPathPredicateItem().setPattern("/api/order-server/**")
                                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                    }
                    {
                        add(new ApiPathPredicateItem().setPattern("/api/user-server/**")
                                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                    }
                });

        ApiDefinition apiUser = new ApiDefinition("api-camunda-server")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {
                    {
                        add(new ApiPathPredicateItem().setPattern("/api/camunda-server/**")
                                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                    }
                });
        definitions.add(apiOrder);
        definitions.add(apiUser);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * GatewayFlowRule：网关限流规则，针对API Gateway的场景定制的限流规则，
     * 可以针对不同的路由或自定义的API分组进行限流，支持针对请求中的参数，标头，来源IP等进行定制化的限流。
     * 用户可以通过GatewayRuleManager.loadRules(rules)手动加载网关规则，
     * 或通过GatewayRuleManager.register2Property(property)注册动态规则源动态推送（推荐方式）
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        //resource：资源名称，可以是网关中的路线名称或用户自定义的API分组名称。
        rules.add(new GatewayFlowRule("api-order-server")
                        //count：限流阈值
                        .setCount(3)
                        //grade：限流指标维度，同限流规则的grade细分。
                        //.setGrade(2)
                        //intervalSec：统计时间窗口，单位是秒，至少是1秒。
                        .setIntervalSec(2)
                //burst：应对突发请求时额外允许的请求数量。
                //.setBurst(1)
                //maxQueueingTimeoutMs：匀速排队模式下的最大排队时间，单位是几分钟，仅在匀速排队模式下生效。
                //.setMaxQueueingTimeoutMs(3)
        );
        //resourceMode：规则是针对API网关的路由（RESOURCE_MODE_ROUTE_ID）
        // 还是用户在Sentinel中定义的API分组（RESOURCE_MODE_CUSTOM_API_NAME），而是路由。
        rules.add(new GatewayFlowRule("api-camunda-server")
                //.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
                .setCount(3)
                .setIntervalSec(1)
        );
        GatewayRuleManager.loadRules(rules);
    }

}
