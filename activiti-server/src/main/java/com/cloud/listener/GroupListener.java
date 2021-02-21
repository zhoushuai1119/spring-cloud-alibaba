package com.cloud.listener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/21 12:59
 * @version: V1.0
 */
//@Component
public class GroupListener implements TaskListener {

    private static final long serialVersionUID = 6364943724277958404L;

    /**
     * 监听器里不能自动注入runtimeService之类的activiti对象
     * 需要手动获取
     */
    //	@Autowired
    //	RuntimeService runtimeService;
    //
    //	@Autowired
    //	TaskService taskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("会签监听");
        //获取流程id
        String exId = delegateTask.getExecutionId();

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();

        Integer all = (Integer) runtimeService.getVariable(exId, "nrOfInstances");
        Integer complete = (Integer) runtimeService.getVariable(exId, "nrOfCompletedInstances");
        System.out.println("会签完成实例数：" + (complete + 1));
        System.out.println("会签总实例数：" + all);
    }

}
