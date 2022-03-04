package com.xxl.job.admin.controller;

import com.cloud.common.beans.response.PageQueryResponse;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/25 11:24
 * @version: v1
 */
@RestController
@RequestMapping("/elasticsearch")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/jobLog")
    public PageQueryResponse<XxlJobLog> getJobLog(@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return elasticsearchService.getJobLog(pageable);
    }

}
