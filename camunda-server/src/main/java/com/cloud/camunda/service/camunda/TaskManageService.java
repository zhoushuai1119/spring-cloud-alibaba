package com.cloud.camunda.service.camunda;

import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.web.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 任务服务
 * @author: 周帅
 * @date: 2021/2/23 10:30
 * @version: V1.0
 */
@Service
@Slf4j
public class TaskManageService {

    @Autowired
    private TaskService taskService;

    /**
     * 获取用户所有的任务
     *
     * @param userId               用户ID
     * @param processDefinitionKey 流程定义key
     * @param processInstanceId    流程实例Id
     */
    public List<Task> getUserTasks(String userId, String processDefinitionKey, String processInstanceId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (CommonUtil.isNotEmpty(userId)) {
            taskQuery.taskAssignee(userId);
        }
        if (CommonUtil.isNotEmpty(processDefinitionKey)) {
            taskQuery.processDefinitionKey(processDefinitionKey);
        }
        if (CommonUtil.isNotEmpty(processInstanceId)) {
            taskQuery.processInstanceId(processInstanceId);
        }
        List<Task> taskList = taskQuery.list();
        return taskList;
    }

    /**
     * 获取用户所有的组任务
     * 组任务办理流程
     * 第一步：查询组任务
     * 指定候选人，查询该候选人当前的待办任务。候选人不能办理任务。
     * 第二步：拾取(claim)任务
     * 该组任务的所有候选人都能拾取。
     * 将候选人的组任务，变成个人任务。原来候选人就变成了该任务的负责人。
     * 如果拾取后不想办理该任务？
     * 需要将已经拾取的个人任务归还到组里边，将个人任务变成了组任务。
     * 第三步：查询个人任务第四步：办理个人任务
     * 查询方式同个人任务部分，根据assignee 查询用户负责的个人任务
     *
     * @param candidateUser        候选人
     * @param processDefinitionKey 流程定义key
     */
    public List<Task> getUserGroupTasks(String candidateUser, String processDefinitionKey) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (CommonUtil.isNotEmpty(processDefinitionKey)) {
            taskQuery.processDefinitionKey(processDefinitionKey);
        }
        List<Task> groupTaskList = taskQuery.taskCandidateUser(candidateUser).list();
        return groupTaskList;
    }

    /**
     * 完成指定任务
     *
     * @param userId    用户ID
     * @param taskId    任务ID
     * @param variables 流程变量
     * @param approveMessage 审批备注
     */
    public BaseResponse<String> completeTask(String userId, String taskId, Map<String, Object> variables, String approveMessage) {
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).singleResult();
        if (task != null) {
            taskService.complete(taskId, variables);
            log.info("用户:{}成功完成任务ID:{}", userId, taskId);
            //添加审核备注
            taskService.createComment(taskId,task.getProcessInstanceId(),approveMessage);
        } else {
            return BaseResponse.createFailResult(ErrorCodeEnum.NOT_TASK_APPROVAL_PERSON);
        }
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 拾取指定任务
     * 当任务被拾取后其他候选人是看不到任务的
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    public BaseResponse claimTask(String userId, String taskId) {
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskCandidateUser(userId).singleResult();
        if (task != null) {
            taskService.claim(taskId, userId);
            log.info("用户:" + userId + "成功拾取任务ID:" + taskId);
        } else {
            return BaseResponse.createFailResult(ErrorCodeEnum.NOT_TASK_APPROVAL_PERSON);
        }
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 当一个候选人拾取任务后不想处理时会进行退回/交接 任务操作
     *
     * @param userId       用户ID
     * @param taskId       任务ID
     * @param returnUserId 交接给谁，如果为空代表将任务回退到组任务，需要后续再次拾取任务
     */
    public BaseResponse returnTask(String userId, String taskId, String returnUserId) {
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskAssignee(userId).singleResult();
        if (task != null) {
            //任务退回  任务ID     null代表没有处理人执行，需要后续再次拾取任务
            //任务交接  任务ID     如果第二个参数不为空代表将该任务交给returnUserId,但是不建议这么使用
            taskService.setAssignee(task.getId(), returnUserId);
            log.info("用户:{}成功退回/交接任务给{}", userId, returnUserId);
        } else {
            return BaseResponse.createFailResult(ErrorCodeEnum.NOT_TASK_APPROVAL_PERSON);
        }
        return BaseResponse.createSuccessResult(null);
    }

}
