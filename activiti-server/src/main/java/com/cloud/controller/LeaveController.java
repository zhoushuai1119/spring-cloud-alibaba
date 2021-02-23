package com.cloud.controller;

import com.cloud.common.beans.KeyValuePair;
import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.entity.activiti.Leave;
import com.cloud.common.service.activiti.LeaveService;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.service.activiti.ProcessService;
import com.cloud.service.activiti.TaskManageService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
     * @return:
     */
    @RequestMapping("/leaveApply")
    public Result<String> leaveApply(String processDefinitionKey, Integer leaveDays) {
        //当前登录人ID,实际可以通过接口获取当前登录人信息
        String currentUserId = "salaboy";
        //部门领导用户ID,可以通过页面选择下一个审批人传到后台
        String departmentManagerUserId = "erdemedeiros";
        //请假申请ID
        Leave leave = new Leave();
        leave.setApplyUserId(currentUserId);
        leave.setStartTime("2021-02-23");
        leave.setEndTime("2021-02-25");
        leave.setLeaveDays(leaveDays);
        leave.setReason("个人原因");
        leave.setType("1");
        leave.setCreateTime(new Date());
        boolean leaveSave = leaveService.save(leave);

        if (leaveSave) {
            //启动流程实例设置流程变量
            Map<String, Object> variables = new HashMap<>();
            //设置申请人ID
            variables.put("apply_user_id", currentUserId);
            //设置部门领导用户ID
            variables.put("department_manager", departmentManagerUserId);
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
                        taskManageService.completeTask(currentUserId, taskId,null);
                    }
                }
            }
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 请假流程审批
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    @RequestMapping("/leaveAprove")
    public Result<String> leaveAprove(String userId, String taskId) {
        Map<String, Object> variables = new HashMap<>(16);
        variables.put("general_manager", "admin");

        KeyValuePair result = taskManageService.completeTask(userId, taskId, variables);
        return ResultUtil.getResult(result);
    }

}
