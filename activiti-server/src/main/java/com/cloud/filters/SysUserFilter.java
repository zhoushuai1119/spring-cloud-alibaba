package com.cloud.filters;

import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.ShiroUserService;
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
    private ShiroUserService shiroUserService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        ShiroUser shiroUser = shiroUserService.findByUsername(userName);
        request.setAttribute(CommonConstant.ShiroCurrentUser.SHIRO_CURRENT_USER, shiroUser);
        return true;
    }

}