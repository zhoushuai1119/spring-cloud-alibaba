package com.cloud.timedjob;

import lombok.Data;

import java.util.Date;

/**
 * 定时任务反馈
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/2/26
 * @Time 上午10:38
 */
@Data
public class TimeBasedJobFeedback {
    /**
     * logId
     */
    Long logId;

    /**
     * 时间戳
     */
    Date timestamp;

    /**
     * 是否成功
     */
    Boolean success;

    /**
     * 备注
     */
    String msg;
}
