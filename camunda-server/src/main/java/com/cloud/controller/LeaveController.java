package com.cloud.controller;

import com.cloud.entity.Leave;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.platform.web.utils.CommonUtil;
import com.cloud.service.LeaveService;
import com.cloud.service.camunda.ProcessService;
import com.cloud.service.camunda.TaskManageService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 请假流程申请
 * @author: 周帅
 * @date: 2021/2/22 10:38
 * @version: V1.0
 */
@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskManageService taskManageService;

    /**
     * @description: 请假流程申请--》 启动请假流程实例
     * @author: 周帅
     * @date: 2021/2/22 10:41
     * @Param: processDefinitionKey 流程定义key
     * @Param: leaveDays 请假天数（>=3 需要经过总经理审批；<3天直接到人事审批）
     * @Param: nextApproveUserId 下一个审批人ID
     * @return:
     */
    @PostMapping("/leaveApply")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> leaveApply(String processDefinitionKey) {
        String currentUserId = "zhoushuai";
        //请假申请ID
        Leave leave = Leave.builder()
                .applyUserId(currentUserId)
                .startTime(LocalDate.now())
                .endTime(LocalDate.now().plusDays(3))
                .leaveDays(3)
                .reason("个人原因")
                .type(1)
                .createTime(LocalDateTime.now()).build();

        boolean leaveSave = leaveService.save(leave);

        //请假主键ID
        String leaveId = String.valueOf(leave.getId());

        if (leaveSave) {
            //启动流程实例设置流程变量
            Map<String, Object> variables = new HashMap<>();
            //设置申请人ID
            variables.put("apply_user_id", currentUserId);
            //设置部门领导用户ID
            variables.put("group_dep", "zhangsan");
            //设置总经理用户ID
            variables.put("group_admin", "lisi");
            //设置HR人事ID
            variables.put("hr_archive", "wangwu");
            //设置请假天数，用于条件判断；注意key要与bpmn配置的EL表达式的参数名一致
            variables.put("leave_days", leave.getLeaveDays());

            //启动流程实例第二个参数businessKey;一般关联请假申请表的ID
            //第三个参数是设置流程变量
            ProcessInstance processInstance = processService.startProcessInstance(processDefinitionKey, leaveId, variables);
            //流程实例ID
            String processInstanceId = processInstance.getId();
            log.info("请假流程实例ID:{}", processInstanceId);
            //查询当前用户的任务
            List<Task> myTaskList = taskManageService.getUserTasks(currentUserId, processDefinitionKey, processInstanceId);
            if (CommonUtil.isNotEmpty(myTaskList)) {
                for (Task myTask : myTaskList) {
                    if (processInstanceId.equals(myTask.getProcessInstanceId())) {
                        String taskId = myTask.getId();
                        taskManageService.completeTask(currentUserId, taskId, null, "请假申请");
                    }
                }
            }
        }
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 请假流程审批
     *
     * @param taskId 任务ID
     * @Param: nextApproveUserId 下一个审批人ID
     * @Param: approveMessage 审批备注
     */
    @PostMapping("/leaveAprove")
    public BaseResponse<String> leaveAprove(String taskId, String approveUserId, String approveMessage) {
        BaseResponse result = taskManageService.completeTask(approveUserId, taskId, null, approveMessage);
        return result;
    }

}
