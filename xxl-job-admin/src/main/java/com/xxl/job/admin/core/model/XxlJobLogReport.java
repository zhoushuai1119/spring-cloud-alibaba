package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

@Data
@TableName("xxl_job_log_report")
public class XxlJobLogReport extends Model<XxlJobLogReport> {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 调度-时间
     */
    @TableField("trigger_day")
    private Date triggerDay;
    /**
     * 运行中-日志数量
     */
    @TableField("running_count")
    private int runningCount;
    /**
     * 执行成功-日志数量
     */
    @TableField("suc_count")
    private int sucCount;
    /**
     * 执行失败-日志数量
     */
    @TableField("fail_count")
    private int failCount;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
