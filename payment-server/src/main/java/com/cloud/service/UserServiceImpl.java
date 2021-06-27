package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.payment.UserService;
import com.cloud.dao.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/27 13:57
 * @version: V1.0
 */
@DubboService
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) throws Exception {
        user.setPassword("12345678");
        user.setUsername("zssss");
        user.setSalt("idjdjdjjd");
        save(user);
       /* if (true) {
            throw new Exception();
        }*/
    }
}
