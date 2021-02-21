package com.cloud.controller;

import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.utils.LogUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.utils.SecurityUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 注意问题：
 * 1.Activiti7和SpringSecurity耦合，需要加入SpringSecurity的依赖和配置，我们可以使用Security中的用户角色组定义流程执行的组
 * 2.流程默认可自动部署，但是需要再resources/processes文件夹，将流程文件放入当中
 * 3.默认历史表不会生成，需要手动开启配置
 * @author: 周帅
 * @date: 2021/2/20 16:25
 * @version: V1.0
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private HistoryService historyService;

    /**
     * 流程部署
     *
     * @param bpmnName   bpmn文件名(不包含扩展名)
     * @param deployName 流程部署名称
     */
    @RequestMapping("/processdeploy")
    public Deployment processdeploy(String bpmnName, String deployName) {
        // 进行部署
        Deployment deployment = repositoryService.createDeployment()
                // 文件夹的名称不能是process
                .addClasspathResource("processes/" + bpmnName + ".bpmn")
                .addClasspathResource("processes/" + bpmnName + ".png")
                .name(deployName)
                .deploy();
        LogUtil.logger("流程部署成功!" + "部署id:"
                + deployment.getId() + ";部署名称:" + deployment.getName(), LogUtil.INFO_LEVEL, null);
        return deployment;
    }

    /**
     * 启动流程示例
     * act_hi_procinst  流程实例表
     * act_hi_taskinst  任务实例
     * act_ru_task  任务表
     * act_ru_execution  执行表
     * act_hi_actinst  活动实例,注意它的end_time字段的含义
     * act_hi_identitylink  参与者信息（部门经理进行审批，参与者就是部门经理）; 参与者信息（流程定义文件分配）
     *
     * @param processKey 流程key
     */
    @RequestMapping("/startInstance")
    public void startInstance(String processKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
        LogUtil.logger("流程" + processKey + "实例启动成功", LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程实例ID: " + instance.getId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程部署ID: " + instance.getDeploymentId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("流程定义ID: " + instance.getProcessDefinitionId(), LogUtil.INFO_LEVEL, null);
        LogUtil.logger("活动ID: " + instance.getActivityId(), LogUtil.INFO_LEVEL, null);
    }

    /**
     * 查询所有流程定义
     */
    @RequestMapping("/queryProcessDefinitionList")
    public Object queryProcessDefinitionList(Integer pageNo, Integer pageSize) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageNo, pageSize);
        // 直接返回processDefinitionList会报空指针异常
        // ============= 对上面查询结果进行封装 ==================
        List<Map<String, Object>> pdResult = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            Map<String, Object> pdMap = new HashMap<>();
            pdMap.put("id", processDefinition.getId());
            pdMap.put("key", processDefinition.getKey());
            pdMap.put("name", processDefinition.getName());
            pdMap.put("version", processDefinition.getVersion());
            pdMap.put("deploymentId", processDefinition.getDeploymentId());
            pdResult.add(pdMap);
        }
        // ================== 封装完成 =========================
        return pdResult;
    }

    /**
     * 删除流程定义
     *
     * @param deploymentId 部署ID
     */
    @RequestMapping("/delProcessDefinition")
    public void delProcessDefinition(String deploymentId) {
        //普通删除，如果该流程定义已有流程实例启动则删除时报错
        repositoryService.deleteDeployment(deploymentId);
        //强制删除,即使该流程定义已有流程实例启动也可以删除
        //repositoryService.deleteDeployment(deploymentId,true);
    }

    /**
     * 查询所有流程实例
     */
    @RequestMapping("/queryProcessInstanceList")
    public Object queryProcessInstanceList(Integer pageNo, Integer pageSize) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList = processInstanceQuery.listPage(pageNo, pageSize);
        // 直接返回processInstanceList会报错
        // ============= 对上面查询结果进行封装 ==================
        List<Map<String, Object>> instResult = new ArrayList<>();
        for (ProcessInstance processInstance : processInstanceList) {
            Map<String, Object> instMap = new HashMap<>();
            instMap.put("id", processInstance.getId());
            instMap.put("processDefinitionId", processInstance.getProcessDefinitionId());
            instMap.put("name", processInstance.getName());
            instMap.put("deploymentId", processInstance.getDeploymentId());
            instResult.add(instMap);
        }
        // ================== 封装完成 =========================
        return instResult;
    }

    /**
     * 获取当前登录人所有的任务
     *
     * @param processDefinitionKey 流程定义key
     */
    @RequestMapping("/getMyTasks")
    public Object getMyTasks(String processDefinitionKey, Integer pageNo, Integer pageSize) {
        String assignee = "salaboy";
        //3.根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
        List<Task> myTaskList = taskService.createTaskQuery()
//                                       .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee).listPage(pageNo, pageSize);
        // 直接返回myTaskList会异常
        // ============= 对上面查询结果进行封装 ==================
        List<Map<String, Object>> taskResult = new ArrayList<>();
        for (Task Mytask : myTaskList) {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("id", Mytask.getId());
            taskMap.put("assignee", Mytask.getAssignee());
            taskMap.put("name", Mytask.getName());
            taskMap.put("processInstanceId", Mytask.getProcessInstanceId());
            taskMap.put("processDefinitionId", Mytask.getProcessDefinitionId());
            taskResult.add(taskMap);
        }
        // ================== 封装完成 =========================
        return taskResult;
    }

    /**
     * 获取所有的任务
     *
     * @return
     */
    @RequestMapping("/getAllTasks")
    public Object getAllTasks(Integer pageNo, Integer pageSize) {
        List<Task> allTaskList = taskService.createTaskQuery().listPage(pageNo, pageSize);
        // 直接返回myTaskList会异常
        // ============= 对上面查询结果进行封装 ==================
        List<Map<String, Object>> allTaskResult = new ArrayList<>();
        for (Task alltask : allTaskList) {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("id", alltask.getId());
            taskMap.put("assignee", alltask.getAssignee());
            taskMap.put("name", alltask.getName());
            taskMap.put("processInstanceId", alltask.getProcessInstanceId());
            taskMap.put("processDefinitionId", alltask.getProcessDefinitionId());
            allTaskResult.add(taskMap);
        }
        // ================== 封装完成 =========================
        return allTaskResult;
    }

    /**
     * 完成指定任务
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    @RequestMapping("/completeTask")
    public Result<String> completeTask(String userId, String taskId) {
        securityUtil.logInAs(userId);
        taskService.complete(taskId);
        LogUtil.logger("完成任务ID:" + taskId, LogUtil.INFO_LEVEL, null);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 查询历史数据
     *
     * @param processInstanceId 流程实例ID
     */
    @RequestMapping("/historicActivityList")
    public Result<List<HistoricActivityInstance>> historicActivityList(String processInstanceId) {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.processInstanceId(processInstanceId);
        //设置查询条件
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.
                orderByHistoricActivityInstanceStartTime().asc().list();
        return ResultUtil.getResult(list);
    }

}
