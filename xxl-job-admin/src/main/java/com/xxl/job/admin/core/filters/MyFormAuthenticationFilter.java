package com.xxl.job.admin.core.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author:zhoushuai
 * 1、用户访问任意一个接口，都先经过formAuthenticationFilter拦截
 * 2、formAuthenticationFilter拦截到了该请求，执行AuthorizingRealm里的登录逻辑。
 * 3、如果登录成功，将会执行redirect到配置的成功的跳转地址。如果登录失败，将会执行redirect到失败的登录地址
 * @Description:
 * @Date:2018-05-23 14:58
 */
@Slf4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //清除原理的请求地址
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.info("*****执行MyFormAuthenticationFilter验证*****");
        //如果需要添加验证码功能，可以在此处添加
        return super.onAccessDenied(request, response, mappedValue);
    }

}
