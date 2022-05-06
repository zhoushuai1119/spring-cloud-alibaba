package com.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.utils.JwtUtil;
import com.cloud.dao.TokenUserMapper;
import com.cloud.entity.TokenUser;
import com.cloud.service.TokenUserService;
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
