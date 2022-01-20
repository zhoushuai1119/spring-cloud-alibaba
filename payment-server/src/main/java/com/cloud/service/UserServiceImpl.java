package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.payment.UserService;
import com.cloud.dao.UserMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
//    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) throws Exception {
        log.info("全局事务ID:{}", RootContext.getXID());
        user.setPassword("12345678");
        user.setPassword("12345678");
        user.setUsername("zssss");
        user.setSalt("idjdjdjjd");
        save(user);
        if (true) {
            throw new Exception("payment出现异常了。。。。");
        }
    }
}
