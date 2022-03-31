package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.TokenUser;

import java.util.Map;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/7 17:17
 * @version: V1.0
 */
public interface TokenUserService extends IService<TokenUser> {

    /**
     * @param userName
     * @description : 通过用户名获取用户信息
     * @author : shuaizhou4
     * @date : 2020-01-14 11:33
     */
    TokenUser findByUsername(String userName);

    /**
     * token校验
     * @param token
     * @return
     */
    Map<String, Object> verify(String token) throws Exception;

}
