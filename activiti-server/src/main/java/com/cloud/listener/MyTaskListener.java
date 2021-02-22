package com.cloud.listener;

import com.alibaba.fastjson.JSON;
import com.cloud.common.utils.LogUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @description: 任务监听器
 * Event --》 Create 任务创建后触发
 *            Assignment 任务分配后触发
 *            Delete 任务完成后触发
 *            All 所有任务都触发
 * 注意事项:
 *   如果任务监听器的notify方法不能正常执行也会影响任务的执行
 * @author: 周帅
 * @date: 2021/2/22 15:11
 * @version: V1.0
 */
@Component
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //也可以指定任务负责人
//        delegateTask.setAssignee("zhangsan");
        LogUtil.logger("监听到任务:"+delegateTask.getId(),LogUtil.INFO_LEVEL,null);
        LogUtil.logger(JSON.toJSONString(delegateTask),LogUtil.INFO_LEVEL,null);
    }
}
