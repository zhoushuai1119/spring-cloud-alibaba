package com.cloud.order.sentinel;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.cloud.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoushuai
 */
@Slf4j
public class ClusterClientInitFunc implements InitFunc {

    @Override
    public void init() {

        //设置token server的地址
        initClientServerAssignProperty();

        //设置业务端状态为集群限流客户端
        initStateProperty();
    }


    /**
     * 设置 token client 需要链接的token server 的地址
     */
    public void initClientServerAssignProperty() {
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ApolloDataSource<>(CommonConstant.TokenServer.TOKEN_SERVER_NAMESPACE,
                CommonConstant.TokenServer.TOKEN_SERVER_RULE_KEY, CommonConstant.TokenServer.DEFAULT_RULES, new ClusterAssignConfigParser());
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    /**
     * 设置业务端状态为集群限流客户端
     */
    private void initStateProperty() {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
    }

}
