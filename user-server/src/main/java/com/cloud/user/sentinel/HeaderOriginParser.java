package com.cloud.user.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: sentinel 授权规则
 * @author: zhou shuai
 * @date: 2022/10/21 13:41
 * @version: v1
 */
@Component
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 获取请求头
        String origin = request.getHeader("origin");
        if (StringUtils.isBlank(origin)) {
            origin = "blank";
        }
        return origin;
    }

}
