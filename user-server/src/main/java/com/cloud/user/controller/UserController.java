package com.cloud.user.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.cloud.common.utils.RedisUtil;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.user.constants.UserConstant;
import com.cloud.user.domain.dto.UserRegisterDTO;
import com.cloud.user.exception.UserErrorCodeEnum;
import com.cloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * UserController
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 登录成功跳转接口;只支持GET请求
     * @return
     */
    @GetMapping("index")
    public BaseResponse index(){
        return BaseResponse.createSuccessResult("登录成功,跳转到此接口");
    }

    /**
     * 用户登录接口
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public BaseResponse userLogin(HttpServletRequest request) {
        String exceptionClassName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        UserErrorCodeEnum errorCodeEnum;
        if(exceptionClassName != null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                errorCodeEnum = UserErrorCodeEnum.UNKNOWN_ACCOUNT_ERROR;
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                errorCodeEnum = UserErrorCodeEnum.INCORRECT_CREDENTIALS_ERROR;
            } else if(UserConstant.RandomCode.RANDOM_CODE_CHECK_EXCEPTION.equals(exceptionClassName)){
                errorCodeEnum = UserErrorCodeEnum.VALIDATE_CODE_CHECK_ERROR;
            } else if(UserConstant.RandomCode.RANDOM_CODE_EXPIRE_EXCEPTION.equals(exceptionClassName)){
                errorCodeEnum = UserErrorCodeEnum.VALIDATE_CODE_EXPIRE_ERROR;
            } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)){
                errorCodeEnum = UserErrorCodeEnum.EXCESSIVE_ATTEMPTS_EEROR;
            } else{
                errorCodeEnum = UserErrorCodeEnum.UNKNOWM_ERROR;
            }
            return BaseResponse.createFailResult(errorCodeEnum);
        }
        return BaseResponse.createSuccessResult(null);
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
        if (Objects.nonNull(redisUtil.get(UserConstant.RandomCode.RANDOM_CODE_KEY))) {
            redisUtil.delKeys(UserConstant.RandomCode.RANDOM_CODE_KEY);
        }
        log.info("生成的验证码为:{}", authCode);
        //将验证码存入redis，设置过期时间为五分钟
        redisUtil.set(UserConstant.RandomCode.RANDOM_CODE_KEY, authCode, 5, TimeUnit.MINUTES);
        return BaseResponse.createSuccessResult(imageBase64Data);
    }

    /**
     * 用户注册
     *
     * @param userRegister
     * @return
     */
    @PostMapping("/register")
    public BaseResponse userRegister(@RequestBody @Valid UserRegisterDTO userRegister) {
        log.info("currentUSer：{}",SecurityUtils.getSubject().getPrincipal());
        userService.userRegister(userRegister);
        return BaseResponse.createSuccessResult(null);
    }


    /**
     * 未登录请求地址
     * @return
     */
    @GetMapping("unLogin")
    public BaseResponse unLogin(){
        log.info("=====未登录请求接口地址=================");
        return BaseResponse.createFailResult(UserErrorCodeEnum.UN_LOGIN_ERROR);
    }

    /**
     * 权限不足,拒绝访问
     * @return
     */
    @GetMapping("unauthorized")
    public BaseResponse permissionDenied(){
        return BaseResponse.createFailResult(UserErrorCodeEnum.PERMISSION_DENIED_ERROR);
    }

}

