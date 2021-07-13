package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.utils.CommonUtil;
import com.cloud.service.activiti.TaskManageService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/23 12:20
 * @version: V1.0
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController{

    @Autowired
    private TaskManageService taskManageService;

    /**
     * 获取我的任务列表
     *
     * @return
     */
    @RequestMapping("/getMyTasks")
    public BaseResponse<List<Map<String, Object>>> getMyTasks() {
        ShiroUser currentUser = getCurrentUser();
        List<Task> myTaskList = taskManageService.getUserTasks(currentUser.getId(),null,null);
        // 直接返回myTaskList会异常
        List<Map<String, Object>> myTaskMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(myTaskList)) {
            myTaskList.forEach(myTask -> {
                Map<String, Object> myTaskMap = new HashMap<>(16);
                myTaskMap.put("taskId", myTask.getId());
                myTaskMap.put("taskName", myTask.getName());
                myTaskMap.put("assignee", myTask.getAssignee());
                myTaskMap.put("processInstanceId", myTask.getProcessInstanceId());
                myTaskMap.put("processDefinitionId", myTask.getProcessDefinitionId());
                myTaskMapList.add(myTaskMap);
            });
        }
        return BaseResponse.createSuccessResult(myTaskMapList);
    }

    /**
     * 获取我的组任务列表
     *
     * @return
     */
    @RequestMapping("/getMyGroupTasks")
    public BaseResponse<List<Map<String, Object>>> getMyGroupTasks() {
        ShiroUser currentUser = getCurrentUser();
        List<Task> myGroupTaskList = taskManageService.getUserGroupTasks(currentUser.getId(),null);
        // 直接返回myTaskList会异常
        List<Map<String, Object>> myGroupTaskMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(myGroupTaskList)) {
            for (Task myGroupTask : myGroupTaskList) {
                Map<String, Object> myGroupTaskMap = new HashMap<>();
                myGroupTaskMap.put("taskId", myGroupTask.getId());
                myGroupTaskMap.put("taskName", myGroupTask.getName());
                myGroupTaskMap.put("assignee", myGroupTask.getAssignee());
                myGroupTaskMap.put("processInstanceId", myGroupTask.getProcessInstanceId());
                myGroupTaskMap.put("processDefinitionId", myGroupTask.getProcessDefinitionId());
                myGroupTaskMapList.add(myGroupTaskMap);
            }
        }
        return BaseResponse.createSuccessResult(myGroupTaskMapList);
    }


    /**
     * 拾取指定任务
     *   当任务被拾取后其他候选人是看不到任务的
     *
     * @param taskId 任务ID
     */
    @RequestMapping("/claimTask")
    public BaseResponse<String> claimTask(String taskId) {
        ShiroUser currentUser = getCurrentUser();
        BaseResponse result = taskManageService.claimTask(currentUser.getId(), taskId);
        return result;
    }

}
