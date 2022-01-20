package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.payment.UserService;
import com.cloud.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/27 13:57
 * @version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) throws Exception {
        user.setPassword("12345678");
        user.setPassword("12345678");
        user.setUsername("zssss");
        save(user);

        System.out.println("jdjdj");
        user.setSalt("idjdjdjjd");
        if (true) {
            throw new Exception();
        }
    }
}
