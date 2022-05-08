package com.cloud.controller;

import com.cloud.filter.RedissionBloomFilter;
import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.platform.common.domain.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class BloomFilterController {

    @Autowired
    private RedissionBloomFilter redissionBloomFilter;

    @GetMapping("/hi")
    public BaseResponse<String> home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("在线人员:{}",session.getServletContext().getAttribute("onlinePersonNum"));
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 使用布隆过滤器 根据ID查询商品
     */
    @GetMapping("/redission/{id}")
    public BaseResponse<String> redission(@PathVariable Integer id){
        //先查询布隆过滤器，过滤掉不可能存在的数据请求
        if (!redissionBloomFilter.isExist(id)) {
            log.error("id:{},Redssion布隆过滤...",id);
            return BaseResponse.createFailResult(ErrorCodeEnum.NOT_LEGAL_ERROR);
        }
        //布隆过滤器认为可能存在，再走流程查询
        return BaseResponse.createSuccessResult(null);
    }

}
