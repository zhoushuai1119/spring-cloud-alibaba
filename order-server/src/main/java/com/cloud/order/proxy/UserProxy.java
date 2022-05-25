package com.cloud.order.proxy;

import com.cloud.order.client.UserClient;
import com.cloud.order.domain.dto.UserRegisterDTO;
import com.cloud.platform.common.domain.response.BaseResponse;
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

    /**
     * 保存用户
     */
    public BaseResponse userRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("lisi");
        userRegisterDTO.setPassword("123456");
        return userClient.userRegister(userRegisterDTO);
    }

}
