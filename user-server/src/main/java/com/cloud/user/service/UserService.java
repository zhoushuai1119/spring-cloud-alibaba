package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.user.domain.dto.UserRegisterDTO;
import com.cloud.user.domain.entity.User;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegister
     */
    void userRegister(UserRegisterDTO userRegister);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User findUserByName(String username);

    /**
     * 获取用户角色列表
     * @param username
     * @return
     */
    Set<String> getUserRoleList(String username);

    /**
     * 获取用户权限列表
     * @param username
     * @return
     */
    Set<String> getUserPermissionList(String username);

}
