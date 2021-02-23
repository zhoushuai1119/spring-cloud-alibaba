package com.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.ShiroUserService;
import com.cloud.dao.ShiroUserMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/23 14:12
 * @version: V1.0
 */
@Service
public class ShiroUserServiceImpl extends ServiceImpl<ShiroUserMapper, ShiroUser> implements ShiroUserService {

    /**
     * @param userName
     * @description : 通过用户名获取用户信息
     * @author : shuaizhou4
     * @date : 2020-01-14 11:33
     */
    @Override
    public ShiroUser findByUsername(String userName) {
        return getOne(new QueryWrapper<ShiroUser>().eq("USER_NAME", userName));
    }

    /**
     * @param userName
     * @description : 获取用户角色
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     */
    @Override
    public Set<String> getRoles(String userName) {
        return baseMapper.getRoles(userName);
    }

    /**
     * @param userName
     * @description : 获取用户操作权限
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     */
    @Override
    public Set<String> getPermissions(String userName) {
        return baseMapper.getPermissions(userName);
    }
}
