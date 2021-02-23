package com.cloud.service.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/23 10:55
 * @version: V1.0
 */
@Service
@Slf4j
public class HistoryManageService {

    @Autowired
    private HistoryService historyService;

    /**
     * 查询某个流程实例的历史数据
     *
     * @param processInstanceId 流程实例ID
     */
    public List<HistoricActivityInstance> historicActivityList(String processInstanceId,Integer pageNo, Integer pageSize) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.processInstanceId(processInstanceId);
        //设置查询条件
        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.
                orderByHistoricActivityInstanceStartTime().asc().listPage(pageNo,pageSize);
        return historicActivityInstanceList;
    }
}
