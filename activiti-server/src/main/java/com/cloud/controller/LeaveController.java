package com.cloud.controller;

import com.cloud.common.beans.KeyValuePair;
import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.entity.activiti.Leave;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.LeaveService;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.service.activiti.ProcessService;
import com.cloud.service.activiti.TaskManageService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class LeaveController extends BaseController {

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
    @RequestMapping("/leaveApply")
    public Result<String> leaveApply(String processDefinitionKey, Integer leaveDays, String nextApproveUserId) {
        ShiroUser currentUser = getCurrentUser();
        String currentUserId = currentUser.getId();
        //请假申请ID
        Leave leave = Leave.builder()
                .applyUserId(currentUserId)
                .startTime("2021-02-23")
                .endTime("2021-02-25")
                .leaveDays(leaveDays)
                .reason("个人原因")
                .type("1")
                .createTime(LocalDateTime.now()).build();

        boolean leaveSave = leaveService.save(leave);

        if (leaveSave) {
            //启动流程实例设置流程变量
            Map<String, Object> variables = new HashMap<>();
            //设置申请人ID
            variables.put("apply_user_id", currentUserId);
            //设置部门领导用户ID
            variables.put("department_manager", nextApproveUserId);
            //设置请假天数，用于条件判断；注意key要与bpmn配置的EL表达式的参数名一致
            variables.put("leave_days", leaveDays);

            //启动流程实例第二个参数businessKey;一般关联请假申请表的ID
            //第三个参数是设置流程变量
            ProcessInstance processInstance = processService.startProcessInstance(processDefinitionKey, leave.getId(), variables);
            //流程实例ID
            String processInstanceId = processInstance.getId();
            //查询当前用户的任务
            List<Task> myTaskList = taskManageService.getUserTasks(currentUserId, processDefinitionKey, processInstanceId);
            if (CommonUtil.isNotEmpty(myTaskList)) {
                for (Task myTask : myTaskList) {
                    if (processInstanceId.equals(myTask.getProcessInstanceId())) {
                        String taskId = myTask.getId();
                        taskManageService.completeTask(currentUserId, taskId, null);
                    }
                }
            }
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 请假流程审批
     *
     * @param taskId 任务ID
     * @Param: nextApproveUserId 下一个审批人ID
     */
    @RequestMapping("/leaveAprove")
    public Result<String> leaveAprove(String taskId, String nextApproveUserId) {
        ShiroUser currentUser = getCurrentUser();
        Map<String, Object> variables = new HashMap<>(16);
        variables.put("general_manager", nextApproveUserId);

        KeyValuePair result = taskManageService.completeTask(currentUser.getId(), taskId, variables);
        return ResultUtil.getResult(result);
    }

    /**
     * 请假流程列表
     */
    @RequestMapping("/leaveList")
    public Result<List<Leave>> leaveList() {
        List<Leave> leaveList = leaveService.getLeaveList();
        if (CommonUtil.isNotEmpty(leaveList)) {
            leaveList.forEach(k ->
                    log.info("**"+ k)
            );
        }
        return ResultUtil.getResult(leaveList);
    }

}
