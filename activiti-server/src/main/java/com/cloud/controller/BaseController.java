package com.cloud.controller;

import com.cloud.common.entity.activiti.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/23 18:44
 * @version: V1.0
 */
public class BaseController {

    public ShiroUser getCurrentUser(){
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }
}
