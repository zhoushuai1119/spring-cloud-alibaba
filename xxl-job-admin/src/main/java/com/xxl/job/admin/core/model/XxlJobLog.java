package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * xxl-job log, used to track trigger process
 *
 * @author xuxueli  2015-12-19 23:19:09
 * indexName: 必须全部小写
 */
@Data
@TableName("xxl_job_log")
@Document(indexName = "idx_joblog")
public class XxlJobLog implements Serializable {

    private static final long serialVersionUID = 4619412463401915979L;

    /**
     * 主键ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 执行器主键ID
     */
    @TableField("job_group")
    private int jobGroup;
    /**
     * 任务，主键ID
     */
    @TableField("job_id")
    private int jobId;

    // execute info

    /**
     * 执行器地址，本次执行的地址
     */
    @TableField("executor_address")
    private String executorAddress;
    /**
     * 执行器任务handler
     */
    @TableField("executor_handler")
    private String executorHandler;
    /**
     * 系统编码
     */
    @TableField("system_code")
    private String systemCode;
    /**
     * 执行器任务参数
     */
    @TableField("executor_param")
    private String executorParam;
    /**
     * 执行器任务分片参数，格式如 1/2
     */
    @TableField("executor_sharding_param")
    private String executorShardingParam;
    /**
     * 失败重试次数
     */
    @TableField("executor_fail_retry_count")
    private Integer executorFailRetryCount;

    // trigger info
    /**
     * 调度-时间
     */
    @TableField("trigger_time")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date triggerTime;
    /**
     * 调度-结果
     */
    @TableField("trigger_code")
    private int triggerCode;
    /**
     * 调度-日志
     */
    @TableField("trigger_msg")
    private String triggerMsg;

    // handle info
    /**
     * 执行-时间
     */
    @TableField("handle_time")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date handleTime;
    /**
     * 执行-状态
     */
    @TableField("handle_code")
    private int handleCode;
    /**
     * 执行-日志
     */
    @TableField("handle_msg")
    private String handleMsg;

    // callback info
    /**
     * 完成-时间
     */
    @TableField("callback_time")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date callbackTime;
    /**
     * 完成-状态
     */
    @TableField("callback_code")
    private int callbackCode;
    /**
     * 完成-日志
     */
    @TableField("callback_msg")
    private String callbackMsg;

    // alarm info
    /**
     * 告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败
     */
    @TableField("alarm_status")
    private int alarmStatus;

}
