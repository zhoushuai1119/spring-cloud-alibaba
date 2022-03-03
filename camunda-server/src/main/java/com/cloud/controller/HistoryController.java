package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.service.camunda.HistoryManageService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/leave/history")
@Slf4j
public class HistoryController {

    @Autowired
    private HistoryManageService historyManageService;

    /**
     * 查询某个流程实例的节点数据
     *
     * @param processInstanceId 流程实例ID
     */
    @PostMapping("/activity/list")
    public BaseResponse<List<Map<String, Object>>> historicActivityList(String processInstanceId) {
        return historyManageService.historicActivityList(processInstanceId);
    }


    /**
     * 请假历史审批记录
     *
     * @param processInstanceId 流程实例ID
     */
    @PostMapping("/records/list")
    public BaseResponse<List<Map<String, Object>>> historyRecordsList(String processInstanceId) {
        return historyManageService.historyRecordsList(processInstanceId);
    }

}
