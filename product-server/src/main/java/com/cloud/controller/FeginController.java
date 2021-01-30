package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ Author： Zhou Shuai
 * @ Date： 14:13 2018/12/28
 * @ Description：
 * @ Version:  01
 */
@RestController
public class FeginController {

    @Value("${server.port}")
    private String port;


    @GetMapping("/getSession")
    public String getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("访问端口:"+port+"sessionId"+session.getId());
        return port+"****"+session.getId();
    }

}
