package com.cloud.filters;

import com.cloud.common.constants.CommonConstant;
import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.ShiroUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Slf4j
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private ShiroUserService shiroUserService;

    @Override
    protected boolean onPreHandle(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        ShiroUser shiroUser = shiroUserService.findByUsername(userName);
        request.getSession().setAttribute(CommonConstant.ShiroCurrentUser.SHIRO_CURRENT_USER, shiroUser);
        log.info("当前登录用户:"+shiroUser.getUserName());
        return true;
    }

}