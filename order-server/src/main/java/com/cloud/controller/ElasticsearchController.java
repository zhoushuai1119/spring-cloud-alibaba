package com.cloud.controller;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.order.Category;
import com.cloud.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 14:13
 * @version: v1
 */
@RestController
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @PostMapping("/saveCategoty")
    public BaseResponse saveCategory() throws Exception {
        return elasticsearchService.saveCategory();
    }

    @GetMapping("/getCategoty")
    public BaseResponse<Category> getCategoty() {
        return elasticsearchService.getCategory();
    }

    @PostMapping("/delCategoty")
    public BaseResponse delCategoty() throws Exception {
        return elasticsearchService.delCategory();
    }

}
