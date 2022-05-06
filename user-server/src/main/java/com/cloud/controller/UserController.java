package com.cloud.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.common.utils.JwtUtil;
import com.cloud.common.utils.RedisUtil;
import com.cloud.config.WebRequestConfig;
import com.cloud.constants.UserConstant;
import com.cloud.dto.UserLoginDTO;
import com.cloud.entity.TokenUser;
import com.cloud.platform.common.response.BaseResponse;
import com.cloud.service.TokenUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author： Zhou Shuai
 * @Date： 14:28 2019/1/7
 * @Description：
 * @Version: 01
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private TokenUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private WebRequestConfig webRequestConfig;


    /**
     * 登录
     **/
    @PostMapping("/login")
    public BaseResponse login(@RequestBody @Valid UserLoginDTO login) {
        Object validateCode = redisUtil.get(UserConstant.AuthCode.IMAGE_AUTH_CODE);
        if (Objects.isNull(validateCode)) {
            return BaseResponse.createFailResult(ErrorCodeEnum.LOGIN_FAIL_VALIDATE_CODE_EXPIRE);
        }
        if (ObjectUtils.notEqual(String.valueOf(validateCode),login.getValidateCode())) {
            return BaseResponse.createFailResult(ErrorCodeEnum.LOGIN_FAIL_VALIDATE_CODE_ERROR);
        }
        TokenUser userForBase = userService.findByUsername(login.getUsername());
        if (userForBase == null) {
            return BaseResponse.createFailResult(ErrorCodeEnum.LOGIN_FAIL_USER_NOT_EXIST);
        } else {
            if (ObjectUtils.notEqual(userForBase.getPassword(), login.getPassword())) {
                return BaseResponse.createFailResult(ErrorCodeEnum.LOGIN_FAIL_PASSWORD_ERROR);
            } else {
                Map<String, Object> claims = new HashMap<>(16);
                claims.put("userId", userForBase.getId());
                claims.put("username", userForBase.getUserName());

                //生成token,设置过期时间默认为12小时
                String token = JwtUtil.sign(claims, userForBase.getId(), null);
                return BaseResponse.createSuccessResult(token);
            }
        }
    }

    /**
     * 获取验证码图片
     *
     * @return
     */
    @GetMapping("/getAuthCodeImg")
    public BaseResponse getAuthCodeImg() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(137, 40, 4, 80);
        lineCaptcha.createCode();
        String authCode = lineCaptcha.getCode();
        String imageBase64Data = lineCaptcha.getImageBase64Data();
        if (Objects.nonNull(redisUtil.get(UserConstant.AuthCode.IMAGE_AUTH_CODE))) {
            redisUtil.delKeys(UserConstant.AuthCode.IMAGE_AUTH_CODE);
        }
        //将验证码存入redis，设置过期时间为五分钟
        redisUtil.set(UserConstant.AuthCode.IMAGE_AUTH_CODE, authCode, 5, TimeUnit.MINUTES);
        return BaseResponse.createSuccessResult(imageBase64Data);
    }


    /**
     * 验证token
     **/
    @PostMapping("/verifyToken")
    public Map<String, Object> verifyToken(String token) {
        return JwtUtil.verify(token);
    }


    /**
     * 获取用户信息
     **/
    @GetMapping("/getUserInfo")
    public BaseResponse getUserInfo() {
        return JwtUtil.getUserInfo(webRequestConfig.getToken());
    }

}
