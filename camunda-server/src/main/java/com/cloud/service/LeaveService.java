package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.Leave;

import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/22 10:16
 * @version: V1.0
 */
public interface LeaveService extends IService<Leave> {

    List<Leave> getLeaveList();

}
