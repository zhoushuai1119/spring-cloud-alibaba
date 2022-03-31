package com.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("leave_process")
public class Leave extends Model<Leave> {

    /**
     * 主键自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 请假申请人ID
     */
    @TableField("apply_user_id")
    private String applyUserId;
    /**
     * 请假开始时间
     */
    @TableField("start_time")
    private LocalDate startTime;
    /**
     * 请假结束时间
     */
    @TableField("end_time")
    private LocalDate endTime;
    /**
     * 请假天数
     */
    @TableField("leave_days")
    private Integer leaveDays;
    /**
     * 请假理由
     */
    @TableField("reason")
    private String reason;
    /**
     * 请假类型
     */
    @TableField("type")
    private Integer type;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

}