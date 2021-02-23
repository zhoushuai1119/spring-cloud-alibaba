package com.cloud.common.service.activiti;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.entity.activiti.ShiroUser;

import java.util.Set;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/22 10:16
 * @version: V1.0
 */
public interface ShiroUserService extends IService<ShiroUser> {

    /**
     * @param userName
     * @description : 通过用户名获取用户信息
     * @author : shuaizhou4
     * @date : 2020-01-14 11:33
     */
    ShiroUser findByUsername(String userName);

    /**
     * @param userName
     * @description : 获取用户角色
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     */
    Set<String> getRoles(String userName);

    /**
     * @param userName
     * @description : 获取用户操作权限
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     */
    Set<String> getPermissions(String userName);

}
