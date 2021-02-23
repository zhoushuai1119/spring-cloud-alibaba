package com.cloud.controller;

import com.cloud.common.beans.Result;
import com.cloud.common.beans.ReturnCode;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.service.activiti.ProcessService;
import org.activiti.engine.repository.ProcessDefinition;
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
 * @date: 2021/2/23 12:15
 * @version: V1.0
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 流程部署
     *  act_re_deployment  流程部署信息
     *  act_re_procdef 流程定义信息
     *  act_ge_bytearray 流程定义的bpmn文件
     *
     * @param bpmnName    bpmn文件名(不包含扩展名)
     * @param deployName  流程部署名称
     * @param isDeployPng 是否部署流程图片
     */
    @RequestMapping("/deploy")
    public Result<String> processdeploy(String bpmnName, String deployName, boolean isDeployPng) {
        processService.processdeploy(bpmnName, deployName, isDeployPng);
        return ResultUtil.getResult(ReturnCode.SUCCESS);
    }

    /**
     * 查询所有流程定义
     *
     * @param processDefinitionKey 流程定义key
     */
    @RequestMapping("/queryProcessDefinitionList")
    public Result<List<Map<String, Object>>> queryProcessDefinitionList(String processDefinitionKey) {
        List<ProcessDefinition> processDefinitionList = processService.queryProcessDefinitionList(processDefinitionKey);
        List<Map<String, Object>> processDefinitionMapList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(processDefinitionList)) {
            for (ProcessDefinition processDefinition : processDefinitionList) {
                Map<String, Object> processDefinitionMap = new HashMap<>(16);
                processDefinitionMap.put("processDefinitionId", processDefinition.getId());
                processDefinitionMap.put("processDefinitionKey", processDefinition.getKey());
                processDefinitionMap.put("deploymentId", processDefinition.getDeploymentId());
                processDefinitionMapList.add(processDefinitionMap);
            }
        }
        return ResultUtil.getResult(processDefinitionMapList);
    }

}
