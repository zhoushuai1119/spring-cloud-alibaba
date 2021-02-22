package com.cloud.controller;

import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.entity.activiti.Leave;
import com.cloud.common.service.activiti.LeaveService;
import com.cloud.common.utils.LogUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.common.utils.UUIDutil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/22 10:38
 * @version: V1.0
 */
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private TaskService taskService;

    /**
     * @description: 请假流程申请--》 启动请假流程实例
     * @author: 周帅
     * @date: 2021/2/22 10:41
     * @Param: processInstanceKey 流程定义key
     * @Param: leaveDays 请假天数（>=3 需要经过总经理审批；<3天直接到人事审批）
     * @return:
     */
    @RequestMapping("/leaveApply")
    public Result<String> leaveApply(String processInstanceKey,Integer leaveDays) {
        //当前登录人ID,实际可以通过接口获取当前登录人信息
        String currentUserId = "salaboy";
        //部门领导用户ID,可以通过页面选择下一个审批人传到后台
        String departmentManagerUserId = "erdemedeiros";
        //请假申请ID
        String leaveApplyId = UUIDutil.getUUID();
        Leave leave = new Leave();
        leave.setId(leaveApplyId);
        leave.setApplyUserId(currentUserId);
        leave.setStartTime("2021-02-23");
        leave.setEndTime("2021-02-25");
        leave.setLeaveDays(leaveDays);
        leave.setReason("个人原因");
        leave.setType("1");
        leave.setCreateTime(new Date());
        leaveService.save(leave);

        //启动流程实例设置流程变量
        Map<String,Object> variables = new HashMap<>();
        //设置申请人ID
        variables.put("apply_user_id",currentUserId);
        //设置部门领导用户ID
        variables.put("department_manager",departmentManagerUserId);
        //设置请假天数，用于条件判断；注意key要与bpmn配置的EL表达式的参数名一致
        variables.put("leave_days",leaveDays);

        //启动流程实例第二个参数businessKey;一般关联请假申请表的ID
        //第三个参数是设置流程变量
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processInstanceKey,leaveApplyId,variables);
        LogUtil.logger("流程" + processInstanceKey + "实例启动成功", LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程实例ID: " + instance.getId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("业务businessKey: " + instance.getBusinessKey(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程部署ID: " + instance.getDeploymentId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程定义ID: " + instance.getProcessDefinitionId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("活动ID: " + instance.getActivityId(), LogUtil.INFO_LEVEL, null);
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
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskAssignee(userId).singleResult();
        if (task != null) {
            //完成任务时设置流程变量;设置下一个审批人ID;
            //实际使用过程中可以前端传过来,也可以后台查询
            Map<String,Object> variables = new HashMap<>();
            variables.put("general_manager","admin");

            taskService.complete(taskId,variables);
            LogUtil.logger("完成任务ID:" + taskId, LogUtil.INFO_LEVEL, null);
        }else {
            return ResultUtil.getResult(ReturnCode.COMPLETE_TASK_ERROR);
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * @description: 组任务部署--》 启动请假流程实例
     * @author: 周帅
     * @date: 2021/2/22 10:41
     * @Param: processInstanceKey 流程定义key
     * @return:
     */
    @RequestMapping("/groupApply")
    public Result<String> groupApply(String processInstanceKey) {
        //启动流程实例设置流程变量
        Map<String,Object> variables = new HashMap<>();
        //设置申请人ID
        variables.put("group_dep","salaboy,other");
        //设置部门领导用户ID
        variables.put("group_admin","ryandawsonuk,admin");
        //第三个参数是设置流程变量
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processInstanceKey,variables);
        LogUtil.logger("流程" + processInstanceKey + "实例启动成功", LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程实例ID: " + instance.getId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程部署ID: " + instance.getDeploymentId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程定义ID: " + instance.getProcessDefinitionId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("活动ID: " + instance.getActivityId(), LogUtil.INFO_LEVEL, null);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

}
