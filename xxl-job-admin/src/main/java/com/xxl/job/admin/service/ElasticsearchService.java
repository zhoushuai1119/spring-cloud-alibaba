package com.xxl.job.admin.service;

import com.cloud.common.beans.response.PageQueryResponse;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.springframework.data.domain.Pageable;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/25 11:29
 * @version: v1
 */
public interface ElasticsearchService {

    /**
     * 创建索引
     * @param clazz
     */
    void createIndex(Class clazz);

    /**
     * 删除索引
     * @param clazz
     */
    void deleteIndex(Class clazz);

    /**
     * 保存jobLog日志
     * @param xxlJobLog
     */
    void saveJobLog(XxlJobLog xxlJobLog);

    /**
     * 分页查询日志
     * @param pageable
     * @return
     */
    PageQueryResponse<XxlJobLog> getJobLog(Pageable pageable);

}
