package com.cloud.user.shiro.filter;

import com.cloud.common.utils.RedisUtil;
import com.cloud.user.constants.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author:zhoushuai
 * @Description:
 * @Date:2018-05-23 14:58
 */
@Slf4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //清除原理的请求地址
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        return false;
    }

    /**
     * 先执行isAccessAllowed()，通过subject.isAuthenticated()判断当前session中的subject是否已经登陆过
     * isAccessAllowed()返回true，authc放行
     * 如果isAccessAllowed()是false即拒绝访问后执行onAccessDenied()方法:
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("======执行自定义过滤=============");
        //如果请求的是loginUrl 并且是POST请求，那么肯定是要验证密码的，这里直接返回false 就会执行onAcessDenied()方法
        if (isLoginRequest(request, response) && isLoginSubmission(request, response)) {
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //获取用户输入的验证码
        String randomCode = httpServletRequest.getParameter("randomCode");
        //从redis中取出验证码
        Object validateCode = redisUtil.get(UserConstant.RandomCode.RANDOM_CODE_KEY);
        if (Objects.isNull(validateCode)) {
            //验证码已过期
            httpServletRequest.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, UserConstant.RandomCode.RANDOM_CODE_EXPIRE_EXCEPTION);
            //拒绝访问，不再校验账号和密码
            return true;
        }
        if (StringUtils.isNotBlank(randomCode) && ObjectUtils.notEqual(randomCode, String.valueOf(validateCode))) {
            //验证码校验失败
            httpServletRequest.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, UserConstant.RandomCode.RANDOM_CODE_CHECK_EXCEPTION);
            //拒绝访问，不再校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

}
