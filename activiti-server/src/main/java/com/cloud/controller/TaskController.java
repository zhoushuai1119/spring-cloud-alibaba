package com.cloud.controller;

import com.cloud.common.beans.KeyValuePair;
import com.cloud.common.beans.Result;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.ResultUtil;
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
public class TaskController {

    @Autowired
    private TaskManageService taskManageService;

    /**
     * 获取我的任务列表
     *
     * @param userId  用户ID
     * @return
     */
    @RequestMapping("/getMyTasks")
    public Result<List<Map<String, Object>>> getMyTasks(String userId) {
        List<Task> myTaskList = taskManageService.getUserTasks(userId,null,null);
        // 直接返回myTaskList会异常
        List<Map<String, Object>> myTaskMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(myTaskList)) {
            for (Task myTask : myTaskList) {
                Map<String, Object> myTaskMap = new HashMap<>();
                myTaskMap.put("taskId", myTask.getId());
                myTaskMap.put("taskName", myTask.getName());
                myTaskMap.put("assignee", myTask.getAssignee());
 //               myTaskMap.put("businessKey", myTask.getBusinessKey());
                myTaskMap.put("processInstanceId", myTask.getProcessInstanceId());
                myTaskMap.put("processDefinitionId", myTask.getProcessDefinitionId());
                myTaskMapList.add(myTaskMap);
            }
        }
        return ResultUtil.getResult(myTaskMapList);
    }

    /**
     * 获取我的组任务列表
     *
     * @param userId  用户ID
     * @return
     */
    @RequestMapping("/getMyGroupTasks")
    public Result<List<Map<String, Object>>> getMyGroupTasks(String userId) {
        List<Task> myGroupTaskList = taskManageService.getUserGroupTasks(userId,null);
        // 直接返回myTaskList会异常
        List<Map<String, Object>> myGroupTaskMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(myGroupTaskList)) {
            for (Task myGroupTask : myGroupTaskList) {
                Map<String, Object> myGroupTaskMap = new HashMap<>();
                myGroupTaskMap.put("taskId", myGroupTask.getId());
                myGroupTaskMap.put("taskName", myGroupTask.getName());
                myGroupTaskMap.put("assignee", myGroupTask.getAssignee());
//                myGroupTaskMap.put("businessKey", myGroupTask.getBusinessKey());
                myGroupTaskMap.put("processInstanceId", myGroupTask.getProcessInstanceId());
                myGroupTaskMap.put("processDefinitionId", myGroupTask.getProcessDefinitionId());
                myGroupTaskMapList.add(myGroupTaskMap);
            }
        }
        return ResultUtil.getResult(myGroupTaskMapList);
    }

    /**
     * 完成指定任务
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    @RequestMapping("/completeTask")
    public Result<String> completeTask(String userId, String taskId) {
        KeyValuePair result = taskManageService.completeTask(userId, taskId,null);
        return ResultUtil.getResult(result);
    }

    /**
     * 拾取指定任务
     *   当任务被拾取后其他候选人是看不到任务的
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    @RequestMapping("/claimTask")
    public Result<String> claimTask(String userId, String taskId) {
        KeyValuePair result = taskManageService.claimTask(userId, taskId);
        return ResultUtil.getResult(result);
    }

}
