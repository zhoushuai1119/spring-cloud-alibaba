package com.cloud.order.proxy;

import com.cloud.common.utils.BusinessUtils;
import com.cloud.order.client.UserClient;
import com.cloud.order.domain.dto.UserRegisterDTO;
import com.cloud.order.service.CategoryService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/20 19:17
 * @version: v1
 */
@Component
@Slf4j
public class UserProxy {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CategoryService categoryService;

    /**
     * 保存用户
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public void userRegister() {
        categoryService.updateCategory("16");
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("lisi");
        userRegisterDTO.setPassword("123456");
        BusinessUtils.checkBaseRespose(userClient.userRegister(userRegisterDTO));
    }

}
