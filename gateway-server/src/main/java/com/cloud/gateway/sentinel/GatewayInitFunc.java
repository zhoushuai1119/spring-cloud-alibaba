package com.cloud.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.gateway.config.ApolloConfigUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author zhoushuai
 */
@Slf4j
public class GatewayInitFunc implements InitFunc {

    /**
     * sentinel 网关规则配置namespace
     */
    private String sentinelGatewayRulesNameSpace = ApolloConfigUtil.getSentinelGatewayRulesNamespace();
    /**
     * defaultRules
     */
    private String defaultRules = "[]";

    @Override
    public void init() {
        String appName = AppNameUtil.getAppName();
        //网关流控规则配置
        registerGatewayFlowRuleProperty(appName);
        //网关API管理规则配置
        registerGatewayApiProperty(appName);
    }


    /**
     * 网关流控规则配置
     *
     * @param appName 应用名称
     */
    private void registerGatewayFlowRuleProperty(String appName) {
        ReadableDataSource<String, Set<GatewayFlowRule>> gatewayFlowRuleDataSource = new ApolloDataSource<>(sentinelGatewayRulesNameSpace,
                ApolloConfigUtil.getGatewayFlowDataId(appName), defaultRules, source -> JSON.parseObject(source,
                new TypeReference<Set<GatewayFlowRule>>() {
                }));
        GatewayRuleManager.register2Property(gatewayFlowRuleDataSource.getProperty());
    }


    /**
     * 网关API管理规则配置
     *
     * @param appName 应用名称
     */
    private void registerGatewayApiProperty(String appName) {
        ReadableDataSource<String, Set<ApiDefinition>> apiDefinitionDataSource = new ApolloDataSource<>(sentinelGatewayRulesNameSpace,
                ApolloConfigUtil.getGatewayApiGroupDataId(appName), defaultRules, source -> JSON.parseObject(source,
                new TypeReference<Set<ApiDefinition>>() {
                }));
        GatewayApiDefinitionManager.register2Property(apiDefinitionDataSource.getProperty());
    }

}
