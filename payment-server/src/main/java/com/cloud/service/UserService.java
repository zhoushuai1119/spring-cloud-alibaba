package com.cloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.UserTest;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/27 13:55
 * @version: V1.0
 */
public interface UserService extends IService<UserTest> {

    void saveUser(UserTest user) throws Exception;

}
