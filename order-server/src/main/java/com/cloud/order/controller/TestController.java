package com.cloud.order.controller;

import com.cloud.common.utils.RedisUtil;
import com.cloud.order.domain.dto.ParmsTestDTO;
import com.cloud.order.enums.CategoryTypeEnum;
import com.cloud.order.proxy.UserProxy;
import com.cloud.order.service.SqlService;
import com.cloud.order.utils.ThreadLocalUtil;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.platform.web.aop.annotation.MethodLogger;
import com.cloud.platform.web.validate.Save;
import com.cloud.platform.web.validate.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/5/9 14:31
 * @version: v1
 */
@RequestMapping("test")
@RestController
@Slf4j
public class TestController {

    @Autowired
    private SqlService sqlService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserProxy userProxy;

    /**
     * 参数校验测试
     *
     * @param parmsTestDto
     * @return
     */
    @PostMapping("/param/check")
    @MethodLogger
    public BaseResponse<ParmsTestDTO> paramCheck(@RequestBody @Validated({Save.class, Update.class}) ParmsTestDTO parmsTestDto) {
        parmsTestDto.setStr(Thread.currentThread().getName());
        ThreadLocalUtil.set(parmsTestDto);
        parmsTestDto.setLocalDateTime(parmsTestDto.getLocalDateTime());
        //redis序列化测试
        redisUtil.set("parmsTestDto", parmsTestDto, 60 * 10, TimeUnit.SECONDS);
        ParmsTestDTO parmsRedis = (ParmsTestDTO) redisUtil.get("parmsTestDto");
        return BaseResponse.createSuccessResult(parmsRedis);
    }

    /**
     * 测试 StringToEnumConverterFactory
     * @param categoryType
     * @return
     */
    @PostMapping("/category/enum")
    public BaseResponse<CategoryTypeEnum> enumTest(CategoryTypeEnum categoryType) {
        return BaseResponse.createSuccessResult(categoryType);
    }

    /**
     * 测试session 监听器
     * @param request
     * @return
     */
    @GetMapping("/listener")
    public BaseResponse<String> testSessionListener(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("在线人员:{}",session.getServletContext().getAttribute("onlinePersonNum"));
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 表名重命名
     * @return
     * @throws SQLException
     */
    @PostMapping("/sql/reNameTable")
    public BaseResponse<String> reNameTable() throws SQLException {
        sqlService.reNameTable(null, null);
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 拷贝表
     * @return
     * @throws SQLException
     */
    @PostMapping("/sql/copyNameTable")
    public BaseResponse<String> copyNameTable() throws SQLException {
        sqlService.copyNameTable(null, null);
        return BaseResponse.createSuccessResult(null);
    }

    @PostMapping("/user/register")
    public BaseResponse<String> userRegister() {
        userProxy.userRegister();
        return BaseResponse.createSuccessResult(null);
    }

}
