package com.cloud.controller;

import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.ShiroUserService;
import com.cloud.shiro.util.PasswordHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @Author： Zhou Shuai
 * @Date： 15:39 2018/12/18
 * @Description：
 * @Version:  01
 */
@Controller
@Api(value="Swagger2生成Restful API文档",tags={"Swagger2生成Restful API文档"})
public class UserController {

    @Autowired
    private ShiroUserService userService;


    @RequestMapping("/save")
    @ResponseBody
    public String save(ShiroUser user){
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(user);
        userService.save(user);
        return "success!!!";
    }

    @RequestMapping("/index")
    public  String userList(Model model){
        ShiroUser user = new ShiroUser();
        List<ShiroUser> userList = user.selectAll();
        model.addAttribute("userList",userList);
        return "user/list";
    }

}
