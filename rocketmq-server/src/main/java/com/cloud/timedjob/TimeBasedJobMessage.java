package com.cloud.timedjob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 定时任务消息
 *
 * @Author Wang Lin(王霖)
 * @Date 2018/2/26
 * @Time 上午10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBasedJobMessage {
    /**
     * logId
     */
    Long logId;

    /**
     * 发送时间
     */
    LocalDateTime timestamp;

}
