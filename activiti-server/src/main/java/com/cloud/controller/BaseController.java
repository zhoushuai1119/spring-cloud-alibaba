package com.cloud.controller;

import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.activiti.ShiroUser;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/23 18:44
 * @version: V1.0
 */
public class BaseController {

    public ShiroUser getCurrentUser(HttpSession session){
        Object currentUser = session.getAttribute(CommonConstant.ShiroCurrentUser.SHIRO_CURRENT_USER);
        if (currentUser != null){
            return (ShiroUser)currentUser;
        }
        return null;
    }
}
