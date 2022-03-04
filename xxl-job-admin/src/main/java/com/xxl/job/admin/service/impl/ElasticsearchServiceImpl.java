package com.xxl.job.admin.service.impl;

import com.cloud.common.beans.response.PageQueryResponse;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.dao.elasticsearch.JobLogElasticRepository;
import com.xxl.job.admin.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/25 14:25
 * @version: v1
 */
@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private JobLogElasticRepository jobLogElasticRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void createIndex(Class clazz) {
        boolean exists = elasticsearchRestTemplate.indexOps(clazz).exists();
        if (exists) {
            return;
        }
        elasticsearchRestTemplate.indexOps(clazz).create();
    }

    @Override
    public void deleteIndex(Class clazz) {
        elasticsearchRestTemplate.delete(clazz);
    }


    @Override
    public void saveJobLog(XxlJobLog xxlJobLog) {
        jobLogElasticRepository.save(xxlJobLog);
    }


    @Override
    public PageQueryResponse<XxlJobLog> getJobLog(Pageable pageable) {
        Page<XxlJobLog> xxlJobLogPage = jobLogElasticRepository.findAll(pageable);
        return PageQueryResponse.createSuccessResult(xxlJobLogPage.getContent(),
                pageable.getPageNumber(),
                (int)xxlJobLogPage.getTotalElements(),
                pageable.getPageSize());
    }

}
