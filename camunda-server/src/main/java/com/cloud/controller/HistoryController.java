package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.service.camunda.HistoryManageService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description: 审批历史记录
 * @author: zhou shuai
 * @date: 2022/3/3 13:41
 * @version: v1
 */
@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryController {

    @Autowired
    private HistoryManageService historyManageService;

    /**
     * 请假流程审批记录
     *
     * @param processInstanceId 流程实例ID
     */
    @PostMapping("/leave/list")
    public BaseResponse<List<Map<String, Object>>> leaveHistory(String processInstanceId) {
        return historyManageService.historicActivityList(processInstanceId,"leave_start","leave_end");
    }


    /**
     * 查询某个流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    @PostMapping("/leave/process/list")
    public BaseResponse<List<HistoricProcessInstance>> historicProcessInstanceQuery(String processInstanceId) {
        return historyManageService.historicProcessInstanceQuery(processInstanceId);
    }

    /**
     * 查询某个流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    @PostMapping("/leave/task/list")
    public BaseResponse<List<HistoricTaskInstance>> historyTaskInstance(String processInstanceId) {
        return historyManageService.historyTaskInstance(processInstanceId);
    }

}
