package com.cloud.order.sentinel;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.entity.ClusterGroupEntity;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhoushuai
 */
@Slf4j
public class ClusterAssignConfigParser implements Converter<String, ClusterClientAssignConfig> {

    @Override
    public ClusterClientAssignConfig convert(String source) {
        if (source == null) {
            return null;
        }
        RecordLog.info("[ClusterClientAssignConfigParser] Get data: " + source);
        //转换成对象List
        List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
        });
        if (groupList == null || groupList.isEmpty()) {
            return null;
        }
        return extractClientAssignment(groupList);
    }

    private ClusterClientAssignConfig extractClientAssignment(List<ClusterGroupEntity> groupList) {
        //获取第一个配置的TokenServer地址信息，解析出ip，端口
        ClusterGroupEntity group = groupList.get(0);
        String ip = group.getIp();
        Integer port = group.getPort();
        log.info("config token server address : {}", JsonUtil.toString(group));
        return new ClusterClientAssignConfig(ip, port);
    }

}

