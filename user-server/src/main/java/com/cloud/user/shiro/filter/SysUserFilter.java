package com.cloud.user.shiro.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.user.constants.UserConstant;
import com.cloud.user.domain.entity.User;
import com.cloud.user.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Administrator
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        request.setAttribute(UserConstant.CurrentUser.CURRENT_USER, user);
        return true;
    }
}