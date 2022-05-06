package com.cloud.interceptor;

import com.cloud.common.utils.JwtUtil;
import com.cloud.config.WebRequestConfig;
import com.cloud.platform.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 拦截器
 * @author: 周帅
 * @date: 2021/1/25 12:14
 * @version: V1.0
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private WebRequestConfig webRequestConfig;

    /**
     * controller 执行之前调用
     **/

    @Override
    public boolean preHandle(HttpServletRequest res, HttpServletResponse rep, Object object) throws Exception {
        log.info("进入LoginInterceptor拦截器");
        String token = webRequestConfig.getToken();
        if (StringUtils.isNotBlank(token)) {
            Map<String, Object> currentUser = JwtUtil.verify(token);
            log.info("currentUser:{}", JsonUtil.toString(currentUser));
            return true;
        }
        return false;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     **/

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle....");
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     * */

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion....");
    }
}
