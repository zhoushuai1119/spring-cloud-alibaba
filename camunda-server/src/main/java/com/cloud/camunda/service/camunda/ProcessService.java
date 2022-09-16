package com.cloud.camunda.service.camunda;

import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.web.utils.CommonUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @description: 流程服务
 * @author: 周帅
 * @date: 2021/2/23 9:56
 * @version: V1.0
 */
@Service
@Slf4j
public class ProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Value("${fdfs.urlPrefix}")
    private String urlPrefix;

    /**
     * 流程部署
     * <p>
     * act_re_deployment  流程部署信息
     * act_re_procdef    流程定义信息
     * act_ge_bytearray 流程定义的bpmn文件
     *
     * @param bpmnName    bpmn文件名(不包含扩展名)
     * @param deployName  流程部署名称
     * @param isDeployPng 是否部署流程图片
     */
    public Deployment processdeploy(String bpmnName, String deployName, boolean isDeployPng) {
        // 进行部署
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                // 文件夹的名称不能是process
                .addClasspathResource("processes/" + bpmnName + ".bpmn")
                .name(deployName);
        if (isDeployPng) {
            deploymentBuilder.addClasspathResource("processes/" + bpmnName + ".png");
        }
        Deployment deployment = deploymentBuilder.deploy();
        log.info("流程部署成功!" + "部署id:"
                + deployment.getId() + ";部署名称:" + deployment.getName());
        return deployment;
    }

    /**
     * 删除流程部署
     * <p>
     * act_re_deployment  流程部署信息
     * act_re_procdef    流程定义信息
     * act_ge_bytearray 流程定义的bpmn文件
     *
     * @param deploymentId 流程部署ID
     */
    public void delProcess(String deploymentId) {
        // 删除流程部署
        repositoryService.deleteDeployment(deploymentId);
        log.info("删除流程成功!部署id:{}", deploymentId);
    }

    /**
     * 流程部署压缩的zip文件
     *
     * @param zipName zip文件名(不包含扩展名)
     */
    public Deployment processdeployByZip(String zipName) {
        //定义zip输入流
        InputStream inputStream = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream("processes/" + zipName + ".zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream).deploy();
        log.info("流程部署成功!" + "部署id:"
                + deployment.getId() + ";部署名称:" + deployment.getName());
        return deployment;
    }

    /**
     * 启动流程示例
     * <p>
     * act_hi_procinst  流程实例表
     * act_hi_taskinst  任务实例
     * act_ru_task  任务表
     * act_ru_execution  执行表
     * act_hi_actinst  活动实例,已完成的活动信息，注意它的end_time为空代表未审批
     * act_hi_identitylink  参与者信息（部门经理进行审批，参与者就是部门经理）; 参与者信息（流程定义文件分配）
     *
     * @param processDefinitionKey 流程定义key
     * @param businessKey          业务key,用户关联业务系统
     * @param variables            流程变量
     */
    public ProcessInstance startProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        log.info("流程" + processDefinitionKey + "实例启动成功");
        log.info("流程实例ID: " + processInstance.getId());
        log.info("业务businessKey: " + processInstance.getBusinessKey());
        return processInstance;
    }

    /**
     * 查询所有流程定义
     *
     * @param processDefinitionKey 流程定义key
     */
    public List<ProcessDefinition> queryProcessDefinitionList(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (CommonUtil.isNotEmpty(processDefinitionKey)) {
            processDefinitionQuery.processDefinitionKey(processDefinitionKey);
        }
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        return processDefinitionList;
    }

    /**
     * 删除流程定义
     * 1: 普通删除，如果该流程定义已有流程实例启动则删除时报错
     * 2: 强制删除,即使该流程定义已有流程实例启动也可以删除
     *
     * @param deploymentId 部署ID
     * @param isForce      是否强制删除
     */
    public void delProcessDefinition(String deploymentId, boolean isForce) {
        repositoryService.deleteDeployment(deploymentId, isForce);
    }

    /**
     * 查询某个流程下的所有流程实例
     *
     * @param processDefinitionKey 流程定义key
     * @param pageNo
     * @param pageSize
     */
    public List<Map<String, Object>> queryProcessInstanceList(String processDefinitionKey, Integer pageNo, Integer pageSize) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceQuery.processDefinitionKey(processDefinitionKey);
        List<ProcessInstance> processInstanceList = processInstanceQuery.listPage(pageNo, pageSize);
        // 直接返回processInstanceList会报错
        // ============= 对上面查询结果进行封装 ==================
        List<Map<String, Object>> processInstanceMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(processInstanceList)) {
            for (ProcessInstance processInstance : processInstanceList) {
                Map<String, Object> processInstanceMap = new HashMap<>();
                processInstanceMap.put("processInstanceId", processInstance.getId());
                processInstanceMap.put("processDefinitionId", processInstance.getProcessDefinitionId());
                processInstanceMap.put("businessKey", processInstance.getBusinessKey());
                //processInstanceMap.put("processDefinitionName", processInstance.getName());
                //processInstanceMap.put("deploymentId", processInstance.getDeploymentId());
                processInstanceMapList.add(processInstanceMap);
            }
        }
        // ================== 封装完成 =========================
        return processInstanceMapList;
    }

    /**
     * 获取流程部署文件
     *
     * @param processDefinitionKey 流程定义key
     */
    public BaseResponse<List<String>> getProcessResource(String processDefinitionKey) throws IOException {
        InputStream png = null;
        InputStream bpmn = null;
        String pngPath = "";
        String bpmnPath = "";
        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            processDefinitionQuery.processDefinitionKey(processDefinitionKey);
            ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
            //流程部署ID
            String deploymentId = processDefinition.getDeploymentId();
            //processDefinition.getDiagramResourceName()指的是图片资源
            png = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
            //processDefinition.getResourceName()指的是bpmn资源
            bpmn = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

            MultipartFile pngMultipartFile = new MockMultipartFile("leave", "leave.png", null, png);
            MultipartFile bpmnMultipartFile = new MockMultipartFile("leave", "leave.bpmn", null, bpmn);

            //pngPath = urlPrefix + fileService.uploadMultipartFile(pngMultipartFile);
            //bpmnPath = urlPrefix + fileService.uploadMultipartFile(bpmnMultipartFile);
        } catch (IOException e) {
            log.error(processDefinitionKey + "获取资源文件失败:" + e.getMessage());
            throw new IOException();
        } finally {
            png.close();
            bpmn.close();
        }
        return BaseResponse.createSuccessResult(Lists.newArrayList(pngPath, bpmnPath));
    }

    /**
     * 流程定义的挂起和激活
     * 流程定义为挂起状态时，该流程定义下边所有的流程实例全部暂停；
     * 且该流程定义将不允许启动新的流程实例
     *
     * @param processDefinitionKey 流程定义key
     */
    public void suspendProcessDefinition(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionKey(processDefinitionKey);
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        //流程定义是否是挂起状态
        boolean suspended = processDefinition.isSuspended();
        String processDefinitionId = processDefinition.getId();
        //suspended为true说明是暂停状态
        if (suspended) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
            log.info("流程定义:" + processDefinitionId + "被激活");
        } else {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            log.info("流程定义:" + processDefinitionId + "被挂起");
        }
    }

    /**
     * 单个流程实例的挂起和激活
     * 流程实例为挂起状态时，该流程定义下边其他的流程实例仍可以执行
     * 完成该流程实例下的任务会报异常
     *
     * @param ProcessInstanceId 流程实例ID
     */
    public void suspendProcessInstance(String ProcessInstanceId) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceQuery.processInstanceId(ProcessInstanceId);
        ProcessInstance processInstance = processInstanceQuery.singleResult();
        //流程实例是否是挂起状态
        boolean suspended = processInstance.isSuspended();
        //suspended为true说明是暂停状态
        if (suspended) {
            runtimeService.activateProcessInstanceById(ProcessInstanceId);
            log.info("流程实例:" + ProcessInstanceId + "被激活");
        } else {
            runtimeService.suspendProcessInstanceById(ProcessInstanceId);
            log.info("流程实例:" + ProcessInstanceId + "被挂起");
        }
    }

}
