package com.xxl.job.admin.core.jobhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/21 22:28
 * @version: v1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutorParamsDTO implements Serializable {

    private static final long serialVersionUID = -9161329636678952313L;

    /**
     * mq: eventCode
     */
    private String eventCode;
    /**
     * XxlJobLog: id
     */
    private long logId;

}
