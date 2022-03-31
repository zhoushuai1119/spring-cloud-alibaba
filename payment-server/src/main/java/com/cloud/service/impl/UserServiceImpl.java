package com.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dao.UserMapper;
import com.cloud.entity.User;
import com.cloud.service.UserService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/27 13:57
 * @version: V1.0
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public void saveUser(User user) throws Exception {
        log.info("全局事务ID:{}", RootContext.getXID());
        user.setPassword("12345678");
        user.setUsername("zssss");
        user.setSalt("idjdjdjjd");
        save(user);
        /*if (true) {
            throw new RuntimeException("payment出现异常了。。。。");
        }*/
    }

}
