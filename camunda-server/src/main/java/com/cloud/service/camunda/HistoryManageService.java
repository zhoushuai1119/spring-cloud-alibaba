package com.cloud.service.camunda;

import com.cloud.common.beans.response.BaseResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
     * 查询某个流程实例的历史数据
     *
     * @param processInstanceId 流程实例ID
     */
    public BaseResponse<List<Map<String, Object>>> historicActivityList(String processInstanceId,String startActivityId,String endActivityId) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //设置查询条件
        historicActivityInstanceQuery.processInstanceId(processInstanceId);
        HistoricActivityInstanceQuery historyQuery = historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        //过滤开始节点和结束节点以及未审批节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyQuery.list().stream()
                        //.filter(activityId -> ObjectUtils.notEqual(activityId.getActivityId(),startActivityId))
                        //.filter(activityId -> ObjectUtils.notEqual(activityId.getActivityId(),endActivityId))
                        //.filter(endTime -> Objects.nonNull(endTime.getEndTime()))
                        .collect(Collectors.toList());
        List<Map<String, Object>> historyRecordsList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(historicActivityInstanceList)) {
            historicActivityInstanceList.forEach(historicActivityInstance -> {
                //审批备注
                List<Comment> commentList = taskService.getTaskComments(historicActivityInstance.getTaskId());
                Map<String, Object> historyRecord = new HashMap<>();
                historyRecord.put("taskId",historicActivityInstance.getTaskId());
                historyRecord.put("activityId",historicActivityInstance.getActivityId());
                historyRecord.put("activityName",historicActivityInstance.getActivityName());
                historyRecord.put("time",historicActivityInstance.getEndTime());
                historyRecord.put("processInstanceId",historicActivityInstance.getProcessInstanceId());
                historyRecord.put("processDefinitionId",historicActivityInstance.getProcessDefinitionId());
                historyRecord.put("processDefinitionKey",historicActivityInstance.getProcessDefinitionKey());
                if (CollectionUtils.isNotEmpty(commentList)) {
                    historyRecord.put("message",commentList.get(0).getFullMessage());
                }
                historyRecordsList.add(historyRecord);
            });
        }
        return BaseResponse.createSuccessResult(historyRecordsList);
    }


    /**
     * 查询某个流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    public BaseResponse<List<HistoricProcessInstance>> historicProcessInstanceQuery(String processInstanceId) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        //设置查询条件
        historicProcessInstanceQuery.processInstanceId(processInstanceId);
        historicProcessInstanceQuery.orderByProcessInstanceStartTime().asc();
        //过滤开始节点和结束节点以及未审批节点
        List<HistoricProcessInstance> historicActivityInstanceList = historicProcessInstanceQuery.list();
        return BaseResponse.createSuccessResult(historicActivityInstanceList);
    }


    /**
     * 查询某个流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    public BaseResponse<List<HistoricTaskInstance>> historyTaskInstance(String processInstanceId) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        //设置查询条件
        historicTaskInstanceQuery.processInstanceId(processInstanceId);
        //过滤开始节点和结束节点以及未审批节点
        List<HistoricTaskInstance> historicActivityInstanceList = historicTaskInstanceQuery.list();
        return BaseResponse.createSuccessResult(historicActivityInstanceList);
    }

}
