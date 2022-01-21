package com.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.common.service.user.TokenUserService;
import com.cloud.dao.TokenUserMapper;
import com.cloud.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/7 17:24
 * @version: V1.0
 */
@Service
public class TokenUserServiceImpl extends ServiceImpl<TokenUserMapper, TokenUser> implements TokenUserService {

    @Override
    public TokenUser findByUsername(String userName) {
        return baseMapper.selectOne(new QueryWrapper<TokenUser>().eq("user_name", userName));
    }

    @Override
    public Map<String, Object> verify(String token) throws Exception {
        return JwtUtil.verify(token);
    }

}
