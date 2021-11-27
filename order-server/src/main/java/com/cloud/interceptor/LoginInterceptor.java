/*
package com.cloud.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @description: 拦截器
 * @author: 周帅
 * @date: 2021/1/25 12:14
 * @version: V1.0
 *//*

@Component
public class LoginInterceptor implements HandlerInterceptor {

    */
/**
     * controller 执行之前调用
     **//*


    @Override
    public boolean preHandle(HttpServletRequest res, HttpServletResponse rep, Object object) throws Exception {
        System.out.println("进入LoginInterceptor拦截器");
        return true;
    }

    */
/**
     * controller 执行之后，且页面渲染之前调用
     **//*


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle....");
    }

    */
/**
     * 页面渲染之后调用，一般用于资源清理操作
     * *//*


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.println("afterCompletion....");
    }
}
*/
