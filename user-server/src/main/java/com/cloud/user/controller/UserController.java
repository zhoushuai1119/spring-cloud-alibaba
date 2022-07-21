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
    public BaseResponse<String> index(){
        return BaseResponse.createSuccessResult("登录成功,跳转到此接口");
    }

    /**
     * 用户登录接口
     * 哪些情况下会请求到当前接口
     *   1.还没有登陆成功的情况下:get请求我们在配置文件中设置的loginUrl，authc会放行请求到这里
     *   2.还没有登陆成功的情况下:post请求我们在配置文件中设置的loginUrl，authc在认证失败后会让浏览器重定向到这里
     * 	 3.还没有登陆成功的情况下:请求(不管post还是get)的页面不是loginUrl且需要authc过滤,那么authc也会让浏览器重定向到这里
     * 	 4.登陆成功后，浏览器再次访问loginUrl(不管post或get)，authc也会放行到这里。如果我们在自定义的过滤器中
     * 	 5.设置成如果是POST请求的loginUrl，我们返回false。那么post请求的loginUrl就会执行在过滤器中执行onAcessDenied()方法,
     * 	 如果post的loginUrl验证失败了会到这里，如果验证成功则到secessUrl这里。
     *
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public BaseResponse<String> userLogin(HttpServletRequest request) {
        log.info("============执行userLogin登录接口=============");
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
    public BaseResponse<String> getAuthCodeImg() {
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
    public BaseResponse<String> userRegister(@RequestBody @Valid UserRegisterDTO userRegister) {
        log.info("currentUSer：{}",SecurityUtils.getSubject().getPrincipal());
        userService.userRegister(userRegister);
        return BaseResponse.createSuccessResult(null);
    }


    /**
     * 未登录请求地址
     * @return
     */
    @GetMapping("unLogin")
    public BaseResponse<String> unLogin(){
        log.info("=====未登录请求接口地址=================");
        return BaseResponse.createFailResult(UserErrorCodeEnum.UN_LOGIN_ERROR);
    }

    /**
     * 权限不足,拒绝访问
     * @return
     */
    @GetMapping("unauthorized")
    public BaseResponse<String> permissionDenied(){
        return BaseResponse.createFailResult(UserErrorCodeEnum.PERMISSION_DENIED_ERROR);
    }

}

