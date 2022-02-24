package com.xxl.job.admin.core.elasticsearch;

import com.xxl.job.admin.core.model.XxlJobLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/24 23:10
 * @version: v1
 */
@Repository
public interface JobLogElasticRepository extends ElasticsearchRepository<XxlJobLog, Long> {

}
