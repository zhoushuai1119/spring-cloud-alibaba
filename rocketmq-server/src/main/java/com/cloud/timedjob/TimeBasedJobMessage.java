package com.cloud.timedjob;

import lombok.Data;

/**
 * 定时任务消息
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/2/26
 * @Time 上午10:38
 */
@Data
public class TimeBasedJobMessage {
    /**
     * logId
     */
    Long logId;

    /**
     * 时间戳
     */
    long timestamp;
}
