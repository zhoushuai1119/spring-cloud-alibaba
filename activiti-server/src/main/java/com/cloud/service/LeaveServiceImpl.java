package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.activiti.Leave;
import com.cloud.common.service.activiti.LeaveService;
import com.cloud.dao.LeaveMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/22 10:16
 * @version: V1.0
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

    @Override
    public List<Leave> getLeaveList() {
        return list();
    }
}
