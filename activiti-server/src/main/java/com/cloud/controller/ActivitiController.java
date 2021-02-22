package com.cloud.controller;

import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.utils.CommonUtil;
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
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

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
     *  act_re_deployment  流程部署信息
     *  act_re_procdef 流程定义信息
     *  act_ge_bytearray 流程定义的bpmn文件
     * @param bpmnName   bpmn文件名(不包含扩展名)
     * @param deployName 流程部署名称
     */
    @RequestMapping("/processdeploy")
    public Deployment processdeploy(String bpmnName, String deployName) {
        // 进行部署
        Deployment deployment = repositoryService.createDeployment()
                // 文件夹的名称不能是process
                .addClasspathResource("processes/" + bpmnName + ".bpmn")
//                .addClasspathResource("processes/" + bpmnName + ".png")
                .name(deployName)
                .deploy();
        LogUtil.logger("流程部署成功!" + "部署id:"
                + deployment.getId() + ";部署名称:" + deployment.getName(), LogUtil.INFO_LEVEL, null);
        return deployment;
    }

    /**
     * 流程部署压缩的zip文件
     */
    @RequestMapping("/processdeployByZip")
    public Deployment processdeployByZip(String zipName) {
        //定义zip输入流
        InputStream inputStream = this
                         .getClass()
                         .getClassLoader()
                         .getResourceAsStream("processes/" + zipName + ".zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment()
                                   .addZipInputStream(zipInputStream).deploy();
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
     * act_hi_actinst  活动实例,已完成的活动信息，注意它的end_time为空代表未审批
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
    public Object queryProcessDefinitionList(String processDefinitionKey,Integer pageNo, Integer pageSize) {
        //String processDefinitionKey = "leave";
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (CommonUtil.isNotEmpty(processDefinitionKey)){
            processDefinitionQuery.processDefinitionKey(processDefinitionKey);
        }
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
            taskMap.put("businessKey",Mytask.getBusinessKey());
            taskResult.add(taskMap);
        }
        // ================== 封装完成 =========================
        return taskResult;
    }

    /**
     * 获取当前登录人所有的组任务
     * 组任务办理流程
     *第一步：查询组任务
     *    指定候选人，查询该候选人当前的待办任务。候选人不能办理任务。
     *第二步：拾取(claim)任务
     *   该组任务的所有候选人都能拾取。
     *   将候选人的组任务，变成个人任务。原来候选人就变成了该任务的负责人。
     *   如果拾取后不想办理该任务？
     *     需要将已经拾取的个人任务归还到组里边，将个人任务变成了组任务。
     *第三步：查询个人任务第四步：办理个人任务
     *   查询方式同个人任务部分，根据assignee 查询用户负责的个人任务
     *
     * @param candidateUser 候选人
     */
    @RequestMapping("/getMyGroupTasks")
    public Object getMyGroupTasks(String candidateUser, Integer pageNo, Integer pageSize) {
//        String candidateUser = "zhangsan";
        //3.根据流程定义的key,候选人candidateUser来实现当前用户的任务列表查询
        List<Task> myTaskList = taskService.createTaskQuery()
//                                       .processDefinitionKey(processDefinitionKey)
                                       .taskCandidateUser(candidateUser)
                                       .listPage(pageNo, pageSize);
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
            taskMap.put("businessKey",Mytask.getBusinessKey());
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
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                                                 .taskAssignee(userId).singleResult();
        if (task != null) {
            taskService.complete(taskId);
            LogUtil.logger("完成任务ID:" + taskId, LogUtil.INFO_LEVEL, null);
        }else {
            return ResultUtil.getResult(ReturnCode.COMPLETE_TASK_ERROR);
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 拾取指定任务
     *   当任务被拾取后其他候选人是看不到任务的
     * @param userId 用户ID
     * @param taskId 任务ID
     */
    @RequestMapping("/claimTask")
    public Result<String> claimTask(String userId, String taskId) {
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                              .taskCandidateUser(userId).singleResult();
        if (task != null) {
            taskService.claim(taskId,userId);
            LogUtil.logger("用户:"+userId+"成功拾取任务ID:" + taskId, LogUtil.INFO_LEVEL, null);
        }else {
            return ResultUtil.getResult(ReturnCode.CLAIM_TASK_ERROR);
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 当一个候选人拾取任务后不想处理时会进行退回/交接 任务操作
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     * @param returnUserId 交接给谁，如果为空代表将任务回退到组任务，需要后续再次拾取任务
     */
    @RequestMapping("/returnTask")
    public Result<String> returnTask(String userId, String taskId,String returnUserId) {
        //完成任务前需要查询一下该用户是否具有操作该任务的权限；
        //因为如果只传一个任务ID，任何人都可以完成该任务
        Task task = taskService.createTaskQuery().taskId(taskId)
                .taskAssignee(userId).singleResult();
        if (task != null) {
            //任务退回  任务ID     null代表没有处理人执行，需要后续再次拾取任务
            //任务交接  任务ID     如果第二个参数，处理人不为空代表将该任务交给returnUserId
            taskService.setAssignee(task.getId(),returnUserId);
            LogUtil.logger("用户:"+userId+"成功退回/交接任务给"+ returnUserId,LogUtil.INFO_LEVEL,null);
        }else {
            return ResultUtil.getResult(ReturnCode.RETURN_TASK_ERROR);
        }
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

    /**
     * 获取流程部署文件
     *
     * @param processKey 流程key
     */
    @RequestMapping("/getProcessResource")
    public Result<String> getProcessResource(String processKey) throws IOException {
        InputStream png = null;
        InputStream bpmn = null;
        OutputStream pngOut = null;
        OutputStream bpmnOut = null;
        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            processDefinitionQuery.processDefinitionKey(processKey);
            ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
            //流程部署ID
            String deploymentId = processDefinition.getDeploymentId();
            //processDefinition.getDiagramResourceName()指的是图片资源
            png = repositoryService.getResourceAsStream(deploymentId,processDefinition.getDiagramResourceName());
            //processDefinition.getResourceName()指的是bpmn资源
            bpmn = repositoryService.getResourceAsStream(deploymentId,processDefinition.getResourceName());
            //构建输入流
            pngOut = new FileOutputStream("D:\\test\\"+processDefinition.getDiagramResourceName());
            bpmnOut = new FileOutputStream("D:\\test\\"+processDefinition.getResourceName());
            //输入输出流的转换；common-io-xx.jar
            IOUtils.copy(png,pngOut);
            IOUtils.copy(bpmn,bpmnOut);
        } catch (IOException e) {
            LogUtil.logger(e.getMessage(),LogUtil.ERROR_LEVEL,e);
            return ResultUtil.getResult(ReturnCode.INTERNAL_ERROR);
        }finally {
            pngOut.close();
            bpmnOut.close();
            png.close();
            bpmn.close();
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 流程定义的挂起和激活
     * 流程定义为挂起状态时，该流程定义下边所有的流程实例全部暂停；
     * 且该流程定义将不允许启动新的流程实例
     *
     * @param processDefinitionKey 流程定义key
     */
    @RequestMapping("/suspendProcessDefinition")
    public Result<String> suspendProcessDefinition(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionKey(processDefinitionKey);
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        //流程定义是否是挂起状态
        boolean suspended = processDefinition.isSuspended();
        String processDefinitionId = processDefinition.getId();
        //suspended为true说明是暂停状态
        if (suspended){
            repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
            LogUtil.logger("流程定义:"+processDefinitionId+"被激活",LogUtil.INFO_LEVEL,null);
        }else {
            repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);
            LogUtil.logger("流程定义:"+processDefinitionId+"被挂起",LogUtil.INFO_LEVEL,null);
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 单个流程实例的挂起和激活
     * 流程实例为挂起状态时，该流程定义下边其他的流程实例仍可以执行
     * 完成该流程实例下的任务会报异常
     *
     * @param ProcessInstanceId 流程实例ID
     */
    @RequestMapping("/suspendProcessInstance")
    public Result<String> suspendProcessInstance(String ProcessInstanceId) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceQuery.processInstanceId(ProcessInstanceId);
        ProcessInstance processInstance = processInstanceQuery.singleResult();
        //流程实例是否是挂起状态
        boolean suspended = processInstance.isSuspended();
        //suspended为true说明是暂停状态
        if (suspended){
            runtimeService.activateProcessInstanceById(ProcessInstanceId);
            LogUtil.logger("流程实例:"+ProcessInstanceId+"被激活",LogUtil.INFO_LEVEL,null);
        }else {
            runtimeService.suspendProcessInstanceById(ProcessInstanceId);
            LogUtil.logger("流程实例:"+ProcessInstanceId+"被挂起",LogUtil.INFO_LEVEL,null);
        }
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

}
