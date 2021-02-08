package com.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.entity.user.TokenUser;
import com.cloud.common.service.user.TokenUserService;
import com.cloud.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author： Zhou Shuai
 * @Date： 14:28 2019/1/7
 * @Description：
 * @Version: 01
 */
@RestController
@RequestMapping("jwt")
public class JwtController {

    @Autowired
    private TokenUserService userService;

    /**
     * 登录
     **/
    @PostMapping("/login")
    public Object login(TokenUser user) {
        JSONObject jsonObject = new JSONObject();
        TokenUser userForBase = userService.findByUsername(user.getUserName());
        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userForBase.getPassword().equals(user.getPassword())) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                Map<String, Object> claims = new HashMap<>(16);
                claims.put("userId", userForBase.getId());
                claims.put("username", userForBase.getUserName());
                claims.put("role", "admin");

                //生成token,设置过期时间为一分钟
                String token = JwtUtil.sign(claims, userForBase.getId(), 3L);
                jsonObject.put("token", token);
                return jsonObject;
            }
        }
    }


    /**
     * 验证token
     **/
    @PostMapping("/verifyToken")
    public Map<String, Object> verifyToken(String token) throws Exception {
        return JwtUtil.verify(token);
    }

}
