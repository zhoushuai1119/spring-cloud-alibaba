package com.cloud.common.service.payment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.entity.payment.User;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/27 13:55
 * @version: V1.0
 */
public interface UserService extends IService<User> {

    void saveUser(User user) throws Exception;

}
