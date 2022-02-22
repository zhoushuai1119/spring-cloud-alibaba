package com.cloud.common.entity.mqjob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/2/22 22:40
 * @version: v1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutorParamsDTO implements Serializable {

    private static final long serialVersionUID = 6427958851628519352L;

    /**
     * mq: eventCode
     */
    private String eventCode;
    /**
     * XxlJobLog: id
     */
    private long logId;

}
