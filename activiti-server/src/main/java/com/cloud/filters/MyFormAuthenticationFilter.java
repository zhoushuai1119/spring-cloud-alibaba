package com.cloud.filters;

import com.cloud.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author:zhoushuai
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
        // 从session获取正确的验证码
        HttpSession session = ((HttpServletRequest) request).getSession();
        //从session中取出验证码
        String validateCode = (String) session.getAttribute("validateCode");
        log.info("session生成的验证码为:"+validateCode);
        //页面输入的验证码
        String randomcode = request.getParameter("randomcode");
        log.info("用户输入的的验证码为:"+randomcode);
        if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
            // randomCodeError表示验证码错误
            request.setAttribute(CommonConstant.ShiroError.LOGIN_ERROR, CommonConstant.ShiroError.RANDOM_CODE_ERROR);
            //拒绝访问，不再校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
}
