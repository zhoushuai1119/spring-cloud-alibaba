package com.cloud.utils;

import com.cloud.common.utils.LogUtil;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstanceMeta;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.*;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/21 13:37
 * @version: V1.0
 */
//@Component
public class ActivitiUtil {

    /**
     * 流程定义相关操作
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 任务相关操作
     */
    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private TaskAdminRuntime taskAdminRuntime;
    /**
     * security工具类
     */
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    private static ActivitiUtil activitiUtil;

    @PostConstruct
    public void init() {
        // processRuntime等为static时无法注入，必须用activitiUtil.processRuntime形式
        activitiUtil = this;
    }

    /**
     * 流程部署
     *
     * @param bpmnName   bpmn文件名(不包含扩展名)
     * @param deployName 流程部署名称
     */
    public static void processdeploy(String bpmnName, String deployName) {
        // 进行部署
        Deployment deployment = activitiUtil.repositoryService.createDeployment()
                // 文件夹的名称不能是process
                .addClasspathResource("processes/" + bpmnName + ".bpmn")
//                .addClasspathResource("processes/" + bpmnName + ".png")
                .name(deployName)
                .deploy();

        LogUtil.logger("流程部署成功！ " + "部署id:"
                + deployment.getId() + "  " + "部署名称:" + deployment.getName(), LogUtil.INFO_LEVEL, null);
    }

    /**
     * 流程实例启动
     *
     * @param processKey  流程Key => 对应bpmn文件里的id
     * @param processName 流程实例名
     */
    public static void startProcessInstance(String username, String processKey, String processName) {
        activitiUtil.securityUtil.logInAs(username);
        ProcessInstance processInstance = activitiUtil.processRuntime
                .start(ProcessPayloadBuilder
                        .start()
                        .withProcessDefinitionKey(processKey)
                        .withProcessDefinitionId("Sample Process: " + new Date())
                        .withName(processName)
                        .build());
        LogUtil.logger("流程实例启动成功: " + processInstance, LogUtil.INFO_LEVEL, null);
    }

    /**
     * 打印所有流程定义
     *
     * @param username 用户名
     */
    public static void printProcessDefinitionList(String username) {
        activitiUtil.securityUtil.logInAs(username);
        Page processDefinitionPage = getProcessDefinitionList(0, 10);
        // getTotalItems()取得的是包含所有版本的总数
        // getContent().size()取得的是流程数，多个版本的同一流程只算一次
        LogUtil.logger("流程定义数: " +
                processDefinitionPage.getContent().size(), LogUtil.INFO_LEVEL, null);
        for (Object pd : processDefinitionPage.getContent()) {
            LogUtil.logger("\t > 流程定义: " + pd, LogUtil.INFO_LEVEL, null);
        }
    }

    /**
     * 获取流程实例
     *
     * @param processInstanceId 流程实例ID
     */
    public static ProcessInstanceMeta getProcessInstanceMeta(String processInstanceId) {
        ProcessInstanceMeta processInstanceMeta = activitiUtil.processRuntime.processInstanceMeta(processInstanceId);
        return processInstanceMeta;
    }

    /**
     * 获取所有流程定义
     *
     * @param startNum 分页开始下标
     * @param endNum   分页结束下标
     * @return 流程定义list
     */
    private static Page getProcessDefinitionList(Integer startNum, Integer endNum) {
        return activitiUtil.processRuntime
                .processDefinitions(Pageable.of(startNum, endNum));
    }

    /**
     * 获取当前登录人所有的任务
     *
     * @return
     */
    public static List<Task> getMyTasks(Integer pageNo, Integer pageSize) {
        Page<Task> tasks = activitiUtil.taskRuntime.tasks(Pageable.of(pageNo, pageSize));
        LogUtil.logger("> My Available Tasks: " + tasks.getTotalItems(), LogUtil.INFO_LEVEL, null);
        return tasks.getContent();
    }

    /**
     * 获取所有的任务
     *
     * @return
     */
    public static List<Task> getAllTasks(Integer pageNo, Integer pageSize) {
        Page<Task> tasks = activitiUtil.taskAdminRuntime.tasks(Pageable.of(pageNo, pageSize));
        LogUtil.logger("> All Available Tasks: " + tasks.getTotalItems(), LogUtil.INFO_LEVEL, null);
        return tasks.getContent();
    }

    /**
     * 完成指定任务
     *
     * @param taskId 任务ID
     */
    public static void completeTask(String taskId) {
        activitiUtil.taskRuntime.complete(TaskPayloadBuilder.complete()
                .withTaskId(taskId).build());
    }

    /**
     * 拾取并完成指定任务
     *
     * @param taskId 任务ID
     */
    public static void claimTask(String taskId) {
        // 拾取任务
        activitiUtil.taskRuntime.claim(
                TaskPayloadBuilder.claim().withTaskId(taskId).build());
        LogUtil.logger("拾取任务ID:" + taskId + "成功", LogUtil.INFO_LEVEL, null);
    }

    /**
     * 拾取并完成指定任务并指定变量
     *
     * @param taskId 任务ID
     */
    public static void claimTaskWithVariables(String taskId, HashMap variables) {
        // 拾取任务
        activitiUtil.taskRuntime.claim(
                TaskPayloadBuilder.claim().withTaskId(taskId).build());
        LogUtil.logger("拾取任务ID:" + taskId + "成功", LogUtil.INFO_LEVEL, null);
        // 完成任务
        activitiUtil.taskRuntime.complete(
                TaskPayloadBuilder.complete()
                        .withTaskId(taskId)
                        .withVariables(variables)
                        .build());
        LogUtil.logger("完成任务ID:" + taskId + "成功", LogUtil.INFO_LEVEL, null);
    }

    /**
     * 查询当前指派人当前业务Key对应的流程实例中的任务
     *
     * @param assignee    指派人
     * @param businessKey 业务key
     * @param startNum    分页开始下标
     * @param endNum      分页结束下标
     * @return 任务list
     */
    public static List<org.activiti.engine.task.Task> getTaskList(String assignee, String businessKey, Integer startNum, Integer endNum) {
        activitiUtil.securityUtil.logInAs(assignee);
        List<org.activiti.engine.task.Task> list = activitiUtil.taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .processInstanceBusinessKey(businessKey)
                .listPage(startNum, endNum);
        return list;
    }

    /**
     * 查询当前指派人当前业务Key对应的流程实例中的任务
     * 某一时间内一个业务key对应的指派人的任务应该是唯一的
     *
     * @param assignee    指派人
     * @param businessKey 业务key
     * @return 任务list
     */
    public static org.activiti.engine.task.Task getTask(String assignee, String businessKey) {
        activitiUtil.securityUtil.logInAs(assignee);
        return activitiUtil.taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .processInstanceBusinessKey(businessKey)
                .singleResult();
    }

    /**
     * 审核(candidate user的场合)
     *
     * @param taskId    任务ID
     * @param auditFlg  审核flg, true：通过，false:驳回
     * @param variables 完成任务时带上变量集合
     * @throws Exception
     */
    public static void auditByCandidate(String taskId, boolean auditFlg, HashMap variables) {
        // 拾取任务
        activitiUtil.taskRuntime.claim(
                TaskPayloadBuilder.claim().withTaskId(taskId).build());
        LogUtil.logger("拾取任务ID:" + taskId + "成功", LogUtil.INFO_LEVEL, null);
        if (auditFlg) {
            // 审核通过的场合
            // 完成任务
            activitiUtil.taskRuntime.complete(
                    TaskPayloadBuilder.complete()
                            .withTaskId(taskId)
                            .withVariables(variables)
                            .build());
            LogUtil.logger("完成任务ID:"+taskId,LogUtil.INFO_LEVEL,null);
            LogUtil.logger("任务ID:"+taskId+"审核通过",LogUtil.INFO_LEVEL,null);
        } else {
            // 驳回的场合
            //backProcess(task);
            LogUtil.logger("任务ID:"+taskId+"审核驳回",LogUtil.INFO_LEVEL,null);
        }
    }

    /**
     * 退回到上一节点
     *
     * @param task 当前任务
     */
    public static void backProcess(Task task) throws Exception {

        String processInstanceId = task.getProcessInstanceId();

        // 取得所有历史任务按时间降序排序
        List<HistoricTaskInstance> htiList = getHistoryTaskList(processInstanceId);

        Integer size = 2;

        if (ObjectUtils.isEmpty(htiList) || htiList.size() < size) {
            return;
        }

        // list里的第二条代表上一个任务
        HistoricTaskInstance lastTask = htiList.get(1);

        // list里第一条代表当前任务
        HistoricTaskInstance curTask = htiList.get(0);

        // 当前节点的executionId
        String curExecutionId = curTask.getExecutionId();


        // 上个节点的taskId
        String lastTaskId = lastTask.getId();
        // 上个节点的executionId
        String lastExecutionId = lastTask.getExecutionId();

        if (null == lastTaskId) {
            throw new Exception("LAST TASK IS NULL");
        }

        String processDefinitionId = lastTask.getProcessDefinitionId();
        BpmnModel bpmnModel = activitiUtil.repositoryService.getBpmnModel(processDefinitionId);

        String lastActivityId = null;
        List<HistoricActivityInstance> haiFinishedList = activitiUtil.historyService.createHistoricActivityInstanceQuery()
                .executionId(lastExecutionId).finished().list();

        for (HistoricActivityInstance hai : haiFinishedList) {
            if (lastTaskId.equals(hai.getTaskId())) {
                // 得到ActivityId，只有HistoricActivityInstance对象里才有此方法
                lastActivityId = hai.getActivityId();
                break;
            }
        }

        // 得到上个节点的信息
        FlowNode lastFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(lastActivityId);

        // 取得当前节点的信息
        Execution execution = activitiUtil.runtimeService.createExecutionQuery().executionId(curExecutionId).singleResult();
        String curActivityId = execution.getActivityId();
        FlowNode curFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(curActivityId);

        //记录当前节点的原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<>();
        oriSequenceFlows.addAll(curFlowNode.getOutgoingFlows());

        //清理活动方向
        curFlowNode.getOutgoingFlows().clear();

        //建立新方向
        List<SequenceFlow> newSequenceFlowList = new ArrayList<>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(curFlowNode);
        newSequenceFlow.setTargetFlowElement(lastFlowNode);
        newSequenceFlowList.add(newSequenceFlow);
        curFlowNode.setOutgoingFlows(newSequenceFlowList);

        // 完成任务
        activitiUtil.taskService.complete(task.getId());

        //恢复原方向
        curFlowNode.setOutgoingFlows(oriSequenceFlows);

        org.activiti.engine.task.Task nextTask = activitiUtil.taskService
                .createTaskQuery().processInstanceId(processInstanceId).singleResult();

        // 设置执行人
        if (nextTask != null) {
            activitiUtil.taskService.setAssignee(nextTask.getId(), lastTask.getAssignee());
        }
    }

    /**
     * 将任务指派给其它人
     *
     * @param taskId
     * @param assigneeOther
     */
    public static void transferTask(String taskId, String assigneeOther) {
        activitiUtil.taskService.setAssignee(taskId, assigneeOther);
        LogUtil.logger("任务已被指派给" + assigneeOther,LogUtil.INFO_LEVEL,null);
    }

    public static List<HistoricTaskInstance> getHistoryTaskList(String processInstanceId) {
        return activitiUtil.historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    public static List<HistoricTaskInstance> getAssigneeHistoryTaskList(String assignee) {
        return activitiUtil.historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime()
                .asc()
                .list();
    }

    /**
     * 获取所有任务节点
     *
     * @throws Exception
     */
    public static FlowElement getAllNode(String processDefinitionId, String processInstanceId, String activityId) {
        // 取得已提交的任务
        HistoricTaskInstance historicTaskInstance = activitiUtil.historyService.createHistoricTaskInstanceQuery()
                .list().get(0);

        //获得当前流程的活动ID
        ExecutionQuery executionQuery = activitiUtil.runtimeService.createExecutionQuery();
        Execution execution = executionQuery.executionId(historicTaskInstance.getExecutionId()).singleResult();
        UserTask userTask;
        while (true) {
            //根据活动节点获取当前的组件信息
            FlowNode flowNode = getFlowNode(processDefinitionId, activityId);
            //获取该节点之后的流向
            List<SequenceFlow> sequenceFlowListOutGoing = flowNode.getOutgoingFlows();

            // 获取的下个节点不一定是userTask的任务节点，所以要判断是否是任务节点
            if (sequenceFlowListOutGoing.size() > 1) {
                // 如果有1条以上的出线，表示有分支，需要判断分支的条件才能知道走哪个分支
                // 遍历节点的出线得到下个activityId
                activityId = getNextActivityId(execution.getId(),
                        processInstanceId, sequenceFlowListOutGoing);
                return getFlowNode(processDefinitionId, activityId);
            } else if (sequenceFlowListOutGoing.size() == 1) {
                // 只有1条出线,直接取得下个节点
                SequenceFlow sequenceFlow = sequenceFlowListOutGoing.get(0);
                // 下个节点
                FlowElement flowElement = sequenceFlow.getTargetFlowElement();
                if (flowElement instanceof UserTask) {
                    // 下个节点为UserTask时
                    userTask = (UserTask) flowElement;
                    return userTask;
                } else if (flowElement instanceof ExclusiveGateway) {
                    // 下个节点为排它网关时
                    ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
                    List<SequenceFlow> outgoingFlows = exclusiveGateway.getOutgoingFlows();
                    // 遍历网关的出线得到下个activityId
                    activityId = getNextActivityId(execution.getId(), processInstanceId, outgoingFlows);
                } else{
                    // 没有出线，则表明是结束节点
                    return null;
                }

            } else {
                // 没有出线，则表明是结束节点
                return null;
            }
        }
    }


    /**
     * 获取当前任务节点的下一个任务节点
     *
     * @param task 当前任务节点
     * @return 下个任务节点
     * @throws Exception
     */
    public static FlowElement getNextUserFlowElement(org.activiti.api.task.model.Task task) throws Exception {
        // 取得已提交的任务
        HistoricTaskInstance historicTaskInstance = activitiUtil.historyService.createHistoricTaskInstanceQuery()
                .taskId(task.getId()).singleResult();

        // 获得流程定义
        ProcessDefinition processDefinition = activitiUtil.repositoryService.getProcessDefinition(historicTaskInstance.getProcessDefinitionId());

        //获得当前流程的活动ID
        ExecutionQuery executionQuery = activitiUtil.runtimeService.createExecutionQuery();
        Execution execution = executionQuery.executionId(historicTaskInstance.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        UserTask userTask = null;
        while (true) {
            //根据活动节点获取当前的组件信息
            FlowNode flowNode = getFlowNode(processDefinition.getId(), activityId);
            //获取该节点之后的流向
            List<SequenceFlow> sequenceFlowListOutGoing = flowNode.getOutgoingFlows();

            // 获取的下个节点不一定是userTask的任务节点，所以要判断是否是任务节点
            if (sequenceFlowListOutGoing.size() > 1) {
                // 如果有1条以上的出线，表示有分支，需要判断分支的条件才能知道走哪个分支
                // 遍历节点的出线得到下个activityId
                activityId = getNextActivityId(execution.getId(),
                        task.getProcessInstanceId(), sequenceFlowListOutGoing);
            } else if (sequenceFlowListOutGoing.size() == 1) {
                // 只有1条出线,直接取得下个节点
                SequenceFlow sequenceFlow = sequenceFlowListOutGoing.get(0);
                // 下个节点
                FlowElement flowElement = sequenceFlow.getTargetFlowElement();
                if (flowElement instanceof UserTask) {
                    // 下个节点为UserTask时
                    userTask = (UserTask) flowElement;
                    System.out.println("下个任务为:" + userTask.getName());
                    return userTask;
                } else if (flowElement instanceof ExclusiveGateway) {
                    // 下个节点为排它网关时
                    ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
                    List<SequenceFlow> outgoingFlows = exclusiveGateway.getOutgoingFlows();
                    // 遍历网关的出线得到下个activityId
                    activityId = getNextActivityId(execution.getId(), task.getProcessInstanceId(), outgoingFlows);
                }

            } else {
                // 没有出线，则表明是结束节点
                return null;
            }
        }
    }

    /**
     * 根据el表达式取得满足条件的下一个activityId
     * @param executionId
     * @param processInstanceId
     * @param outgoingFlows
     * @return
     */
    public static String getNextActivityId(String executionId,
                                           String processInstanceId,
                                           List<SequenceFlow> outgoingFlows) {
        String activityId = null;
        // 遍历出线
        for (SequenceFlow outgoingFlow : outgoingFlows) {
            // 取得线上的条件
            String conditionExpression = outgoingFlow.getConditionExpression();
            // 取得所有变量
            Map<String, Object> variables = activitiUtil.runtimeService.getVariables(executionId);
            String variableName = "";
            // 判断网关条件里是否包含变量名
            for (String s : variables.keySet()) {
                if (conditionExpression.contains(s)) {
                    // 找到网关条件里的变量名
                    variableName = s;
                }
            }
            String conditionVal = getVariableValue(variableName, processInstanceId);
            // 判断el表达式是否成立
            if (isCondition(variableName, conditionExpression, conditionVal)) {
                // 取得目标节点
                FlowElement targetFlowElement = outgoingFlow.getTargetFlowElement();
                activityId = targetFlowElement.getId();
                continue;
            }
        }
        return activityId;
    }

    /**
     * 根据活动节点和流程定义ID获取该活动节点的组件信息
     */
    public static FlowNode getFlowNode(String processDefinitionId, String flowElementId) {
        BpmnModel bpmnModel = activitiUtil.repositoryService.getBpmnModel(processDefinitionId);
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(flowElementId);
        return flowNode;
    }

    /**
     * 取得流程变量的值
     *
     * @param variableName      变量名
     * @param processInstanceId 流程实例Id
     * @return
     */
    public static String getVariableValue(String variableName, String processInstanceId) {
        Execution execution = activitiUtil.runtimeService
                .createExecutionQuery().processInstanceId(processInstanceId).list().get(0);
        Object object = activitiUtil.runtimeService.getVariable(execution.getId(), variableName);
        return object == null ? "" : object.toString();
    }

    /**
     * 根据key和value判断el表达式是否通过
     *
     * @param key   el表达式key
     * @param el    el表达式
     * @param value el表达式传入值
     * @return
     */
    public static boolean isCondition(String key, String el, String value) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable(key, factory.createValueExpression(value, String.class));
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
        return (Boolean) e.getValue(context);
    }

}
