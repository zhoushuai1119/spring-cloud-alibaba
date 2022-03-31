package com.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dao.LeaveMapper;
import com.cloud.entity.Leave;
import com.cloud.service.LeaveService;
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
