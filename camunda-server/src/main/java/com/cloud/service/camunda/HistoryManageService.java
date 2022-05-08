package com.cloud.service.camunda;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private TaskService taskService;

    /**
     * 查询某个流程实例的节点数据
     *
     * @param processInstanceId 流程实例ID
     */
    public BaseResponse<List<Map<String, Object>>> historicActivityList(String processInstanceId) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //设置查询条件
        historicActivityInstanceQuery.processInstanceId(processInstanceId);
        historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        //过滤开始节点和结束节点以及未审批节点
        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery.list();
        List<Map<String, Object>> historyRecordsList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(historicActivityInstanceList)) {
            historicActivityInstanceList.forEach(historicActivityInstance -> {
                Map<String, Object> historyRecord = new HashMap<>();
                historyRecord.put("taskId",historicActivityInstance.getTaskId());
                historyRecord.put("activityId",historicActivityInstance.getActivityId());
                historyRecord.put("activityName",historicActivityInstance.getActivityName());
                historyRecord.put("startTime",historicActivityInstance.getStartTime());
                historyRecord.put("endTime",historicActivityInstance.getEndTime());
                historyRecord.put("processInstanceId",historicActivityInstance.getProcessInstanceId());
                historyRecord.put("processDefinitionId",historicActivityInstance.getProcessDefinitionId());
                historyRecord.put("processDefinitionKey",historicActivityInstance.getProcessDefinitionKey());
                historyRecordsList.add(historyRecord);
            });
        }
        return BaseResponse.createSuccessResult(historyRecordsList);
    }


    /**
     * 请假历史审批记录
     *
     * @param processInstanceId 流程实例ID
     */
    public BaseResponse<List<Map<String, Object>>> historyRecordsList(String processInstanceId) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        //设置查询条件
        historicTaskInstanceQuery.processInstanceId(processInstanceId);
        List<HistoricTaskInstance> historicTaskInstanceList = historicTaskInstanceQuery.list();
        List<Map<String, Object>> historyRecordsList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(historicTaskInstanceList)) {
            historicTaskInstanceList.forEach(historicTaskInstance -> {
                //审批备注
                List<Comment> commentList = taskService.getTaskComments(historicTaskInstance.getId());
                Map<String, Object> historyRecord = new HashMap<>();
                historyRecord.put("taskId",historicTaskInstance.getId());
                historyRecord.put("name",historicTaskInstance.getName());
                historyRecord.put("assignee",historicTaskInstance.getAssignee());
                historyRecord.put("time",historicTaskInstance.getEndTime());
                historyRecord.put("processInstanceId",historicTaskInstance.getProcessInstanceId());
                historyRecord.put("processDefinitionId",historicTaskInstance.getProcessDefinitionId());
                historyRecord.put("processDefinitionKey",historicTaskInstance.getProcessDefinitionKey());
                if (CollectionUtils.isNotEmpty(commentList)) {
                    historyRecord.put("message",commentList.get(0).getFullMessage());
                }
                historyRecordsList.add(historyRecord);
            });
        }
        return BaseResponse.createSuccessResult(historyRecordsList);
    }

}
