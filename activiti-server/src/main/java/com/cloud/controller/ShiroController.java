package com.cloud.controller;

import com.cloud.common.constants.CommonConstant;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : shuaizhou4
 * @version : [V1.0]
 * @description:
 * @email : shuaizhou4@iflytek.com
 * @Company : 科大讯飞
 * @Date ： 2020-08-17 15:54
 */
@Controller
@Api(value="Shiro测试Controller",tags={"Shiro测试Controller"})
public class ShiroController {

    @RequestMapping("/")
    public String index(){
        return "user/login";
    }

    /**用户登陆提交*/
    @RequestMapping("/login")
    public String loginsubmit(HttpServletRequest request, Model model){
        // shiro在认证过程中出现错误后将异常类路径通过request返回
        String exceptionClassName = (String) request.getAttribute(CommonConstant.ShiroError.LOGIN_ERROR);
        if(exceptionClassName != null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                model.addAttribute("error","账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                model.addAttribute("error","用户名/密码错误");
            } else if(CommonConstant.ShiroError.RANDOM_CODE_ERROR.equals(exceptionClassName)){
                model.addAttribute("error","验证码错误");
            } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)){
                model.addAttribute("error","登录失败多次，账户锁定10分钟");
            } else{
                model.addAttribute("error","未知错误");
            }
        }
        return "user/login";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("msg","admin");
        return "user/success";
    }

    @RequestMapping("/student")
    public String student(Model model){
        model.addAttribute("msg","student");
        return "user/success";
    }

    @RequestMapping("/teacher")
    public String teacher(Model model){
        model.addAttribute("msg","teacher");
        return "user/success";
    }


    @RequestMapping("/zhuce")
    public String zhuce(){
        return "user/zhuce";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "user/unauthorized";
    }

}
